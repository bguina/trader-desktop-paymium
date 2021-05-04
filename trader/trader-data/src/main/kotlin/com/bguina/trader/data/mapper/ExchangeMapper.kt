package com.bguina.trader.data.mapper

import com.bguina.trader.data.mapper.base.ABiMapper
import com.bguina.trader.data.mapper.base.IBiMapper
import com.bguina.trader.data.trader.model.EExchangeEntity
import com.bguina.trader.data.trader.model.ExchangeEntity
import com.bguina.trader.domain.model.EExchange
import com.bguina.trader.domain.model.ExchangeStatus
import javax.inject.Inject

class ExchangeMapper @Inject constructor(
    private val exchangeMapper: IBiMapper<EExchangeEntity, EExchange>
) : ABiMapper<ExchangeEntity, ExchangeStatus>(ExchangeEntity::class, ExchangeStatus::class) {
    override val mapper: AInnerMapper = InnerMapper()
    override val reverseMapper: AInnerReverseMapper = InnerReverseMapper()

    inner class InnerMapper : AInnerMapper() {
        override fun map(
            value: ExchangeEntity
        ): ExchangeStatus = ExchangeStatus(
            which = value.which.run(exchangeMapper::map),
            name = value.name,
            status = when (value.status) {
                ExchangeEntity.EStatus.UNKNOWN -> ExchangeStatus.EStatus.UNKNOWN
                ExchangeEntity.EStatus.UNAVAILABLE -> ExchangeStatus.EStatus.UNAVAILABLE
                ExchangeEntity.EStatus.API_QUOTA_REACHED -> ExchangeStatus.EStatus.API_QUOTA_REACHED
                ExchangeEntity.EStatus.ORDER_QUOTA_REACHED -> ExchangeStatus.EStatus.ORDER_QUOTA_REACHED
                ExchangeEntity.EStatus.READY -> ExchangeStatus.EStatus.READY
            }
        )
    }

    inner class InnerReverseMapper : AInnerReverseMapper() {
        override fun map(
            value: ExchangeStatus
        ): ExchangeEntity = ExchangeEntity(
            which = value.which.run(exchangeMapper::reverseMap),
            name = value.name,
            status = when (value.status) {
                ExchangeStatus.EStatus.UNKNOWN -> ExchangeEntity.EStatus.UNKNOWN
                ExchangeStatus.EStatus.UNAVAILABLE -> ExchangeEntity.EStatus.UNAVAILABLE
                ExchangeStatus.EStatus.API_QUOTA_REACHED -> ExchangeEntity.EStatus.API_QUOTA_REACHED
                ExchangeStatus.EStatus.ORDER_QUOTA_REACHED -> ExchangeEntity.EStatus.ORDER_QUOTA_REACHED
                ExchangeStatus.EStatus.READY -> ExchangeEntity.EStatus.READY
            }
        )
    }
}
