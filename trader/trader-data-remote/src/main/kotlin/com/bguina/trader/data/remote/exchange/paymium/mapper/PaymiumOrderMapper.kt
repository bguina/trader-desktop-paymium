package com.bguina.trader.data.remote.exchange.paymium.mapper

import com.bguina.api.paymium.model.v1.user.orders.PaymiumOrderOperationDTO
import com.bguina.api.paymium.model.v1.user.orders.PaymiumUserOrderDTO
import com.bguina.trader.data.mapper.ExchangeOrderOperationEntity
import com.bguina.trader.data.mapper.base.ABiMapper
import com.bguina.trader.data.mapper.base.IBiMapper
import com.bguina.trader.data.trader.TradeEntity
import com.bguina.trader.data.trader.model.*
import javax.inject.Inject

class PaymiumOrderMapper @Inject constructor(
    private val currencyDtoMapper: IBiMapper<String, EAssetEntity>,
    private val operationDtoMapper: IBiMapper<PaymiumOrderOperationDTO, ExchangeOrderOperationEntity>,
) : ABiMapper<ExchangeOrderEntity, PaymiumUserOrderDTO>(ExchangeOrderEntity::class, PaymiumUserOrderDTO::class) {
    override val mapper: AInnerMapper = InnerMapper()
    override val reverseMapper: AInnerReverseMapper = InnerReverseMapper()

    inner class InnerMapper : AInnerMapper() {
        override fun map(
            value: ExchangeOrderEntity
        ): PaymiumUserOrderDTO = PaymiumUserOrderDTO(
            uuid = value.id,
            account_operations = null
        )
    }

    inner class InnerReverseMapper : AInnerReverseMapper() {
        override fun map(
            value: PaymiumUserOrderDTO
        ): ExchangeOrderEntity = ExchangeOrderEntity(
            exchange = EExchangeEntity.PAYMIUM,
            id = value.uuid ?: throwMissingFieldException("uuid"),
            createdAt = value.created_at,
            updatedAt = value.updated_at,
            status = when (value.state) {
                PaymiumUserOrderDTO.ORDER_STATE_PENDING_EXECUTION -> ExchangeOrderEntity.EStatus.PENDING
                PaymiumUserOrderDTO.ORDER_STATE_ACTIVE -> ExchangeOrderEntity.EStatus.ACTIVE
                PaymiumUserOrderDTO.ORDER_STATE_EXECUTED -> ExchangeOrderEntity.EStatus.EXECUTED
                PaymiumUserOrderDTO.ORDER_STATE_FILLED -> ExchangeOrderEntity.EStatus.EXECUTED
                PaymiumUserOrderDTO.ORDER_STATE_CANCELED -> ExchangeOrderEntity.EStatus.CANCELED
                null -> throw throwMissingFieldException("state")
                else -> throw IllegalArgumentException("unknown order state ${value.state}")
            },
            expectedTrade = value.totalTrade,
            executedTrade = value.executedTrade,
            operations = value.account_operations?.map(operationDtoMapper::map)
        )
    }

    private val PaymiumUserOrderDTO.tradedBtc: AssetEntity
        get() = AssetEntity(
            amount = traded_btc?.toDouble(),
            currency = EAssetEntity.BITCOIN
        )

    private val PaymiumUserOrderDTO.tradedCurrency: AssetEntity
        get() = AssetEntity(
            amount = traded_currency?.toDouble(),
            currency = currency?.run(currencyDtoMapper::map)
        )

    private val PaymiumUserOrderDTO.executedTrade: TradeEntity
        get() = when (direction) {
            PaymiumUserOrderDTO.ORDER_DIRECTION_SELL -> tradedBtc to tradedCurrency
            PaymiumUserOrderDTO.ORDER_DIRECTION_BUY -> tradedCurrency to tradedBtc
            else -> throw NotImplementedError("unknown order direction $direction")
        }
            .let { TradeEntity(EMarketEntity.BTC_EUR, it.first, it.second) }

    private val PaymiumUserOrderDTO.totalBtc: AssetEntity
        get() = AssetEntity(
            amount = amount?.toDouble(),
            currency = EAssetEntity.BITCOIN
        )

    private val PaymiumUserOrderDTO.totalCurrency: AssetEntity
        get() = AssetEntity(
            amount = amount?.toDouble()?.let { it * price!!.toDouble() },
            currency = currency?.run(currencyDtoMapper::map)
        )

    private val PaymiumUserOrderDTO.totalTrade: TradeEntity
        get() = when (direction) {
            PaymiumUserOrderDTO.ORDER_DIRECTION_SELL -> totalBtc to totalCurrency
            PaymiumUserOrderDTO.ORDER_DIRECTION_BUY -> totalCurrency to totalBtc
            else -> throw NotImplementedError("unknown order direction $direction")
        }
            .let { TradeEntity(EMarketEntity.BTC_EUR, it.first, it.second) }
}
