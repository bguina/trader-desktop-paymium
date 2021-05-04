package com.bguina.trader.di

import com.bguina.trader.TraderApp
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        SchedulersModule::class,
        LoggerModule::class,
        TraderModule::class
    ]
)
interface TraderComponent {

    fun inject(app: TraderApp)

    @Component.Factory
    interface Builder {
        fun create(
        ): TraderComponent
    }
}
