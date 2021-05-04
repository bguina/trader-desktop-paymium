package com.bguina.trader.data.remote.di

import com.bguina.trader.data.di.ExchangeRemoteDataSourceKey
import com.bguina.trader.data.remote.exchange.paymium.PaymiumRemoteDataSource
import com.bguina.trader.data.trader.IExchangeRemoteDataSource
import com.bguina.trader.data.trader.model.EExchangeEntity
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(
    includes = [
        PaymiumHttpClientModule::class,
                PaymiumMappersModule::class,
    ]
)
interface PaymiumExchangeDataRemoteModule {
    @[Binds IntoMap ExchangeRemoteDataSourceKey(value = EExchangeEntity.PAYMIUM)]
    fun bindPaymiumRemoteDataSource(impl: PaymiumRemoteDataSource): IExchangeRemoteDataSource
}
