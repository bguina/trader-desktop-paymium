package com.bguina.trader.data.mapper

import com.bguina.trader.data.mapper.base.ABiMapper
import com.bguina.trader.data.trader.model.EExchangeEntity
import com.bguina.trader.data.trader.model.EMarketEntity
import com.bguina.trader.domain.model.EExchange
import com.bguina.trader.domain.model.EMarket
import javax.inject.Inject

class EMarketMapper @Inject constructor(
) : ABiMapper<EMarketEntity, EMarket>(EMarketEntity::class, EMarket::class) {
    override val mapper: AInnerMapper = InnerMapper()
    override val reverseMapper: AInnerReverseMapper = InnerReverseMapper()

    inner class InnerMapper : AInnerMapper() {
        override fun map(
            value: EMarketEntity
        ): EMarket = when (value) {
            EMarketEntity.BTC_EUR -> EMarket.BTC_EUR
            EMarketEntity.BTC_USD -> EMarket.BTC_USD
        }
    }

    inner class InnerReverseMapper : AInnerReverseMapper() {
        override fun map(
            value: EMarket
        ): EMarketEntity = when (value) {
            EMarket.BTC_EUR -> EMarketEntity.BTC_EUR
            EMarket.BTC_USD -> EMarketEntity.BTC_USD
        }
    }
}
