package com.bguina.trader.domain.usecase.order.impl

import com.bguina.trader.domain.IExchangeRepository
import com.bguina.trader.domain.di.TraderNamedDependencies
import com.bguina.trader.domain.model.EExchange
import com.bguina.trader.domain.model.ExchangeAccount
import com.bguina.trader.domain.model.ExchangeOrder
import com.bguina.trader.domain.usecase.order.IListExchangeAccountOrdersUseCase
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class ListExchangeAccountOrdersUseCase @Inject constructor(
    @Named(TraderNamedDependencies.SCHEDULER_UI) private val appScheduler: Scheduler,
    private val exchangeRepository: IExchangeRepository
) : IListExchangeAccountOrdersUseCase {
    override fun getExchangeUserOrders(
        exchange: EExchange
    ): Single<List<ExchangeOrder>> = exchangeRepository.listOrders(
        account = ExchangeAccount(
            exchange = exchange,
            login = ""
        )
    )
        .observeOn(appScheduler)
}
