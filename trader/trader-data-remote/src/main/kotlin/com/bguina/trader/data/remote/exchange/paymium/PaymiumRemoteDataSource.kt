package com.bguina.trader.data.remote.exchange.paymium

import com.bguina.api.paymium.model.v1.user.PaymiumUserDTO
import com.bguina.api.paymium.model.v1.user.orders.PaymiumUserOrderDTO
import com.bguina.api.paymium.rx2.Rx2PaymiumApiClient
import com.bguina.trader.data.mapper.base.IBiMapper
import com.bguina.trader.data.mapper.base.IMapper
import com.bguina.trader.data.trader.IExchangeRemoteDataSource
import com.bguina.trader.data.trader.model.*
import io.reactivex.Completable
import io.reactivex.Single
import java.time.chrono.ChronoPeriod
import javax.inject.Inject

class PaymiumRemoteDataSource @Inject constructor(
    private val apiClient: Rx2PaymiumApiClient,
    private val accountMapper: IMapper<PaymiumUserDTO, ExchangeAccountEntity>,
    private val accountOrderMapper: IBiMapper<ExchangeOrderEntity, PaymiumUserOrderDTO>
) : IExchangeRemoteDataSource {

    override fun getExchange(
        account: ExchangeAccountEntity
    ): Single<ExchangeEntity> = apiClient.privateGetUser().map { user ->
        ExchangeEntity(
            which = account.exchange,
            name = EXCHANGE_NAME,
            status = when (user.meta_state) {
                PaymiumUserDTO.META_STATE_APPROVED -> ExchangeEntity.EStatus.READY
                else -> ExchangeEntity.EStatus.UNAVAILABLE
            }
        )
    }

    override fun listAssets(
        assets: Set<EAssetEntity>
    ): Single<Map<EAssetEntity, Double>> = apiClient.privateGetUser()
        .map { user ->
            assets.associateWith {
                when (it) {
                    EAssetEntity.EURO -> (user.balance_eur?.toDouble() ?: .0)
                    EAssetEntity.BITCOIN -> (user.balance_btc?.toDouble() ?: .0)
                    else -> .0
                }
            }
        }

    override fun getAssetValue(
        market: EMarketEntity,
        asset: AssetEntity
    ): Single<AssetEntity> = apiClient.getTicker()
        .map {
            AssetEntity(
                amount = it.price?.toDouble(),
                currency = EAssetEntity.EURO
            )
        }

    override fun listAccountOrders(
        account: ExchangeAccountEntity,
        period: ChronoPeriod?,
        status: Set<ExchangeOrderEntity.EStatus>?
    ): Single<List<ExchangeOrderEntity>> = apiClient.privateListUserOrders(
        offset = 0,
        limit = 50,
        // active = false,//false != status?.none { it == TradeOrderEntity.EStatus.CANCELED },
    ).map { it.map(accountOrderMapper::reverseMap) }

    override fun submitTradeOrder(
        market: EMarketEntity,
        buy: Boolean,
        amount: Double,
        limit: Double?
    ): Single<ExchangeOrderEntity> = when (market) {
        EMarketEntity.BTC_EUR -> apiClient.privatePostOrder(
            buy = buy,
            btcAmount = amount,
            limitPrice = limit
        )
        EMarketEntity.BTC_USD -> throw NotImplementedError("$EXCHANGE_NAME does not support market ${market.name}")
    }.map(accountOrderMapper::reverseMap)

    override fun cancelOrder(
        orderId: String
    ): Completable = apiClient.privateDeleteUserOrderMarketOrder(
        orderId = orderId
    )

    companion object {
        private const val EXCHANGE_NAME = "Paymium"
    }
}
