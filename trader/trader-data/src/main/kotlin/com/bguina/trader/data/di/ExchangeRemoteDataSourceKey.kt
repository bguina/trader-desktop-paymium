package com.bguina.trader.data.di

import com.bguina.trader.data.trader.model.EExchangeEntity
import dagger.MapKey

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ExchangeRemoteDataSourceKey(val value: EExchangeEntity)
