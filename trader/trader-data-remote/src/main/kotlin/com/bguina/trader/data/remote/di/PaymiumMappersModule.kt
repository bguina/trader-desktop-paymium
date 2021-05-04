package com.bguina.trader.data.remote.di

import com.bguina.api.paymium.model.v1.user.PaymiumUserDTO
import com.bguina.api.paymium.model.v1.user.orders.PaymiumOrderOperationDTO
import com.bguina.api.paymium.model.v1.user.orders.PaymiumUserOrderDTO
import com.bguina.trader.data.mapper.ExchangeOrderOperationEntity
import com.bguina.trader.data.mapper.base.IBiMapper
import com.bguina.trader.data.mapper.base.IMapper
import com.bguina.trader.data.remote.exchange.paymium.mapper.PaymiumCurrencyMapper
import com.bguina.trader.data.remote.exchange.paymium.mapper.PaymiumExchangeAccountMapper
import com.bguina.trader.data.remote.exchange.paymium.mapper.PaymiumOrderMapper
import com.bguina.trader.data.remote.exchange.paymium.mapper.PaymiumOrderOperationMapper
import com.bguina.trader.data.trader.model.EAssetEntity
import com.bguina.trader.data.trader.model.ExchangeAccountEntity
import com.bguina.trader.data.trader.model.ExchangeOrderEntity
import dagger.Binds
import dagger.Module

@Module
interface PaymiumMappersModule {
    @Binds
    fun bindPaymiumAccountMapper(impl: PaymiumExchangeAccountMapper): IMapper<PaymiumUserDTO, ExchangeAccountEntity>

    @Binds
    fun bindPaymiumCurrencyMapper(impl: PaymiumCurrencyMapper): IBiMapper<String, EAssetEntity>

    @Binds
    fun bindPaymiumOrderMapper(impl: PaymiumOrderMapper): IBiMapper<ExchangeOrderEntity, PaymiumUserOrderDTO>

    @Binds
    fun bindPaymiumOrderOperationMapper(impl: PaymiumOrderOperationMapper): IBiMapper<PaymiumOrderOperationDTO, ExchangeOrderOperationEntity>

}
