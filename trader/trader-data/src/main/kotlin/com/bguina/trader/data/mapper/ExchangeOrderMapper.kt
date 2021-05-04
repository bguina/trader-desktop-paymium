package com.bguina.trader.data.mapper

import com.bguina.trader.data.mapper.base.ABiMapper
import com.bguina.trader.data.mapper.base.IBiMapper
import com.bguina.trader.data.trader.TradeEntity
import com.bguina.trader.data.trader.model.AssetEntity
import com.bguina.trader.data.trader.model.EExchangeEntity
import com.bguina.trader.data.trader.model.ExchangeOrderEntity
import com.bguina.trader.domain.model.*
import javax.inject.Inject

class ExchangeOrderMapper @Inject constructor(
    private val exchangeMapper: IBiMapper<EExchangeEntity, EExchange>,
    private val statusMapper: IBiMapper<ExchangeOrderEntity.EStatus, ExchangeOrder.EStatus>,
    private val tradeMapper: IBiMapper<TradeEntity, Trade>,
    private val operationMapper: IBiMapper<ExchangeOrderOperationEntity, ExchangeOrderOperation>
) : ABiMapper<ExchangeOrderEntity, ExchangeOrder>(ExchangeOrderEntity::class, ExchangeOrder::class) {
    override val mapper: AInnerMapper = InnerMapper()
    override val reverseMapper: AInnerReverseMapper = InnerReverseMapper()

    inner class InnerMapper : AInnerMapper() {
        override fun map(
            value: ExchangeOrderEntity
        ): ExchangeOrder = ExchangeOrder(
            exchange = value.exchange?.run(exchangeMapper::map) ?: throwMissingFieldException("exchange"),
            id = value.id ?: throwMissingFieldException("id"),
            createdAt = value.createdAt ?: throwMissingFieldException("createdAt"),
            updatedAt = value.updatedAt ?: throwMissingFieldException("updatedAt"),
            status = value.status?.run(statusMapper::map) ?: throwMissingFieldException("status"),
            expectedTrade=value.expectedTrade?.run(tradeMapper::map)?: throwMissingFieldException("expectedTrade"),
            executedTrade = value.executedTrade?.run(tradeMapper::map)?: throwMissingFieldException("executedTrade"),
            operations = value.operations?.map(operationMapper::map).orEmpty(),
        )
    }

    inner class InnerReverseMapper : AInnerReverseMapper() {
        override fun map(
            value: ExchangeOrder
        ): ExchangeOrderEntity = ExchangeOrderEntity(
            exchange = value.exchange.run(exchangeMapper::reverseMap),
            id = value.id,
            createdAt = value.createdAt,
            updatedAt = value.updatedAt,
            status = value.status.run(statusMapper::reverseMap),
            expectedTrade= value.expectedTrade.run(tradeMapper::reverseMap),
            executedTrade = value.executedTrade.run(tradeMapper::reverseMap),
            operations = value.operations.map(operationMapper::reverseMap),
        )
    }
}
