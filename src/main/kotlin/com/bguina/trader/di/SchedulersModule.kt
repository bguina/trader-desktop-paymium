package com.bguina.trader.di

import com.bguina.trader.domain.di.TraderNamedDependencies
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler
import javax.inject.Named

@Module
object SchedulersModule {
    @[Provides Named(TraderNamedDependencies.SCHEDULER_UI)]
    fun provideUiScheduler(): Scheduler = JavaFxScheduler.platform()
}
