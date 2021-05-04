package com.bguina.trader.data.di

import com.bguina.trader.data.trader.ExchangeRepository
import com.bguina.trader.domain.IExchangeRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(
    includes = [
        TraderMappersModule::class
    ]
)
interface TraderDataModule {
    @[Binds Singleton JvmSuppressWildcards]
    fun bindExchangeRepository(impl: ExchangeRepository): IExchangeRepository
}
