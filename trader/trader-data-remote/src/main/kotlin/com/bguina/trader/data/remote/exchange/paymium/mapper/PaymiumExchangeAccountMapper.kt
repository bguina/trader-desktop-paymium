package com.bguina.trader.data.remote.exchange.paymium.mapper

import com.bguina.api.paymium.model.v1.user.PaymiumUserDTO
import com.bguina.trader.data.mapper.base.AMapper
import com.bguina.trader.data.trader.model.EExchangeEntity
import com.bguina.trader.data.trader.model.ExchangeAccountEntity
import javax.inject.Inject

class PaymiumExchangeAccountMapper @Inject constructor(
) : AMapper<PaymiumUserDTO, ExchangeAccountEntity>(PaymiumUserDTO::class, ExchangeAccountEntity::class) {
    override fun map(
        value: PaymiumUserDTO
    ): ExchangeAccountEntity = ExchangeAccountEntity(
        exchange = EExchangeEntity.PAYMIUM,
        login = value.name ?: throwMissingFieldException("PaymiumUserDTO.name")
    )
}
