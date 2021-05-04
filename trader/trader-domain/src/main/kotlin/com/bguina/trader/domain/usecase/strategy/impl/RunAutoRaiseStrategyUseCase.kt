package com.bguina.trader.domain.usecase.strategy.impl

import com.bguina.trader.domain.IExchangeRepository
import com.bguina.trader.domain.di.TraderNamedDependencies
import com.bguina.trader.domain.model.Asset
import com.bguina.trader.domain.model.EExchange
import com.bguina.trader.domain.model.EMarket
import com.bguina.trader.domain.usecase.strategy.IRunAutoRaiseStrategyUseCase
import io.reactivex.Flowable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class RunAutoRaiseStrategyUseCase @Inject constructor(
    @Named(TraderNamedDependencies.SCHEDULER_UI) private val appScheduler: Scheduler,
    private val exchangeRepository: IExchangeRepository
): IRunAutoRaiseStrategyUseCase {
    override fun runAutoRaise(
        exchange: EExchange,
        market: EMarket,
        direction: EMarket.EDirection,
        initialOffer: Asset,
        limit: Double?
    ) : Flowable<Double> = Flowable.error<Double>(NotImplementedError())
        .observeOn(appScheduler)
}
