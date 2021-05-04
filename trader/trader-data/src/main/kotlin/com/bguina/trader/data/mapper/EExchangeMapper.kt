package com.bguina.trader.data.mapper

import com.bguina.trader.data.mapper.base.ABiMapper
import com.bguina.trader.data.trader.model.EExchangeEntity
import com.bguina.trader.domain.model.EExchange
import javax.inject.Inject

class EExchangeMapper @Inject constructor(
) : ABiMapper<EExchangeEntity, EExchange>(EExchangeEntity::class, EExchange::class) {
    override val mapper: AInnerMapper = InnerMapper()
    override val reverseMapper: AInnerReverseMapper = InnerReverseMapper()

    inner class InnerMapper : AInnerMapper() {
        override fun map(
            value: EExchangeEntity
        ): EExchange = when (value) {
            EExchangeEntity.PAYMIUM -> EExchange.PAYMIUM
        }
    }

    inner class InnerReverseMapper : AInnerReverseMapper() {
        override fun map(
            value: EExchange
        ): EExchangeEntity = when (value) {
            EExchange.PAYMIUM -> EExchangeEntity.PAYMIUM
        }
    }
}
