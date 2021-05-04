package com.bguina.trader.data.trader

import com.bguina.trader.data.mapper.base.IBiMapper
import com.bguina.trader.data.trader.model.*
import com.bguina.trader.domain.IExchangeRepository
import com.bguina.trader.domain.model.*
import io.reactivex.Completable
import io.reactivex.Single
import java.time.chrono.ChronoPeriod
import javax.inject.Inject

@JvmSuppressWildcards
class ExchangeRepository @Inject constructor(
    private val exchanges: Map<EExchangeEntity, IExchangeRemoteDataSource>,
    private val exchangeAccountMapper: IBiMapper<ExchangeAccountEntity, ExchangeAccount>,
    private val eExchangeMapper: IBiMapper<EExchangeEntity, EExchange>,
    private val exchangeStatusMapper: IBiMapper<ExchangeEntity, ExchangeStatus>,
    private val marketMapper: IBiMapper<EMarketEntity, EMarket>,
    private val eAssetMapper: IBiMapper<EAssetEntity, EAsset>,
    private val assetMapper: IBiMapper<AssetEntity, Asset>,
    private val tradeMapper: IBiMapper<TradeEntity, Trade>,
    private val orderMapper: IBiMapper<ExchangeOrderEntity, ExchangeOrder>
) : IExchangeRepository {

    override fun getTrader(
        account: ExchangeAccount,
        exchange: EExchange
    ): Single<ExchangeStatus> = getExchangeDataSource(
        exchange = exchange
    ).getExchange(account.run(exchangeAccountMapper::reverseMap))
        .map(exchangeStatusMapper::map)

    override fun listAssets(
        exchange: EExchange,
        assets: Set<EAsset>
    ): Single<Map<EAsset, Double>> = getExchangeDataSource(
        exchange = exchange
    ).listAssets(
        assets = assets.map(eAssetMapper::reverseMap).toSet()
    )
        .map { it.mapKeys { it.key.run(eAssetMapper::map) } }

    override fun getAsset(
        userId: Long,
        exchange: EExchange,
        asset: EAsset
    ): Single<Double> = TODO()

    override fun submitTradeOrder(
        exchange: EExchange,
        market: EMarket,
        direction: EMarket.EDirection,
        amount: Double,
        limit: Double?
    ): Single<ExchangeOrder> = getExchangeDataSource(
        exchange = exchange
    )
        .submitTradeOrder(
            market = market.run(marketMapper::reverseMap),
            buy = when (direction) {
                EMarket.EDirection.SELL -> false
                EMarket.EDirection.BUY -> true
            },
            amount = amount,
            limit = limit
        )
        .map(orderMapper::map)

    override fun listOrders(
        account: ExchangeAccount,
        period: ChronoPeriod?,
        status: ExchangeOrder.EStatus?
    ): Single<List<ExchangeOrder>> = getExchangeDataSource(account.exchange)
        .listAccountOrders(
            account = account.run(exchangeAccountMapper::reverseMap)
        )
        .map { it.map(orderMapper::map) }

    override fun cancelOrder(
        account: ExchangeAccount,
        orderId: String
    ): Completable = getExchangeDataSource(account.exchange)
        .cancelOrder(orderId = orderId)

    override fun getAssetPrice(
        exchanges: Set<EExchange>,
        market: EMarket,
        asset: Asset
    ): Single<Map<EExchange, Asset>> = Single.merge(exchanges
        .map { it to getExchangeDataSource(it) }
        .map { (exchange, rds) ->
            if (market in exchange.markets) {
                rds.getAssetValue(
                    market = market.run(marketMapper::reverseMap),
                    asset = asset.run(assetMapper::reverseMap)
                )
                    .map(assetMapper::map)
                    .map { exchange to it }
            } else {
                Single.error(IllegalAccessException("exchange $exchange does not support market $market"))
            }
        })
        .toList()
        .map { it.toMap() }

    override fun getApplicableFee(
        account: ExchangeAccount,
        exchange: EExchange,
        order: ExchangeOrder
    ): Single<Asset> = TODO()

    private fun getExchangeDataSource(
        exchange: EExchange
    ): IExchangeRemoteDataSource = exchanges[exchange.run(eExchangeMapper::reverseMap)]
        ?: throw NoSuchElementException("trader ${exchange.name}")

}
