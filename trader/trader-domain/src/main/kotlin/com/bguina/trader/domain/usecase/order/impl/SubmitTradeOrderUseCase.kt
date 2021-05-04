package com.bguina.trader.domain.usecase.order.impl

import com.bguina.trader.domain.IExchangeRepository
import com.bguina.trader.domain.di.TraderNamedDependencies
import com.bguina.trader.domain.model.EExchange
import com.bguina.trader.domain.model.EMarket
import com.bguina.trader.domain.model.ExchangeOrder
import com.bguina.trader.domain.usecase.order.ISubmitTradeOrderUseCase
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class SubmitTradeOrderUseCase @Inject constructor(
    @Named(TraderNamedDependencies.SCHEDULER_UI) private val appScheduler: Scheduler,
    private val tradeRepository: IExchangeRepository
) : ISubmitTradeOrderUseCase {
    override fun submitTradeOrder(
        exchange: EExchange,
        market: EMarket,
        direction: EMarket.EDirection,
        amount: Double,
        limit: Double?
    ): Single<ExchangeOrder> = tradeRepository.submitTradeOrder(
        exchange = exchange,
        market = market,
        direction = direction,
        amount = amount,
        limit = limit
    )
        .observeOn(appScheduler)
}
