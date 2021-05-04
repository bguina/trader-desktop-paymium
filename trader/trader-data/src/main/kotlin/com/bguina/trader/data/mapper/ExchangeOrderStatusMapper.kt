package com.bguina.trader.data.mapper

import com.bguina.trader.data.mapper.base.ABiMapper
import com.bguina.trader.data.trader.model.ExchangeOrderEntity
import com.bguina.trader.domain.model.ExchangeOrder
import javax.inject.Inject

class ExchangeOrderStatusMapper @Inject constructor(
) : ABiMapper<ExchangeOrderEntity.EStatus, ExchangeOrder.EStatus>(
    ExchangeOrderEntity.EStatus::class,
    ExchangeOrder.EStatus::class
) {
    override val mapper: AInnerMapper = InnerMapper()
    override val reverseMapper: AInnerReverseMapper = InnerReverseMapper()

    inner class InnerMapper : AInnerMapper() {
        override fun map(
            value: ExchangeOrderEntity.EStatus
        ): ExchangeOrder.EStatus = when (value) {
            ExchangeOrderEntity.EStatus.PENDING -> ExchangeOrder.EStatus.PENDING
            ExchangeOrderEntity.EStatus.ACTIVE -> ExchangeOrder.EStatus.ACTIVE
            ExchangeOrderEntity.EStatus.EXECUTED -> ExchangeOrder.EStatus.EXECUTED
            ExchangeOrderEntity.EStatus.CANCELED -> ExchangeOrder.EStatus.CANCELED
        }
    }

    inner class InnerReverseMapper : AInnerReverseMapper() {
        override fun map(
            value: ExchangeOrder.EStatus
        ): ExchangeOrderEntity.EStatus = when (value) {
            ExchangeOrder.EStatus.PENDING -> ExchangeOrderEntity.EStatus.PENDING
            ExchangeOrder.EStatus.ACTIVE -> ExchangeOrderEntity.EStatus.ACTIVE
            ExchangeOrder.EStatus.EXECUTED -> ExchangeOrderEntity.EStatus.EXECUTED
            ExchangeOrder.EStatus.CANCELED -> ExchangeOrderEntity.EStatus.CANCELED
        }
    }
}
