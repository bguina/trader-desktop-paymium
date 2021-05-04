package com.bguina.trader.data.mapper

import com.bguina.trader.data.mapper.base.ABiMapper
import com.bguina.trader.data.mapper.base.IBiMapper
import com.bguina.trader.data.trader.model.EExchangeEntity
import com.bguina.trader.data.trader.model.ExchangeAccountEntity
import com.bguina.trader.domain.model.EExchange
import com.bguina.trader.domain.model.ExchangeAccount
import javax.inject.Inject

class ExchangeAccountMapper @Inject constructor(
    private val exchangeMapper: IBiMapper<EExchangeEntity, EExchange>
) : ABiMapper<ExchangeAccountEntity, ExchangeAccount>(ExchangeAccountEntity::class, ExchangeAccount::class) {
    override val mapper: AInnerMapper = InnerMapper()
    override val reverseMapper: AInnerReverseMapper = InnerReverseMapper()

    inner class InnerMapper : AInnerMapper() {
        override fun map(
            value: ExchangeAccountEntity
        ): ExchangeAccount = ExchangeAccount(
            exchange = value.exchange.run(exchangeMapper::map),
            login = value.login
        )
    }

    inner class InnerReverseMapper : AInnerReverseMapper() {
        override fun map(
            value: ExchangeAccount
        ): ExchangeAccountEntity = ExchangeAccountEntity(
            exchange = value.exchange.run(exchangeMapper::reverseMap),
            login = value.login
        )
    }
}
