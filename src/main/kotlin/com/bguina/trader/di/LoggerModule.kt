package com.bguina.trader.di

import dagger.Module
import dagger.Provides
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Module
object LoggerModule {
    @Provides
    fun bindLogger(): Logger = LogManager.getLogger(LoggerModule::class.java)
}
