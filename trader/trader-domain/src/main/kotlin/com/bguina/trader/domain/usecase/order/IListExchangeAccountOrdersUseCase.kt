package com.bguina.trader.domain.usecase.order

import com.bguina.trader.domain.model.EExchange
import com.bguina.trader.domain.model.ExchangeOrder
import io.reactivex.Single

interface IListExchangeAccountOrdersUseCase {
    fun getExchangeUserOrders(
        exchange: EExchange
    ): Single<List<ExchangeOrder>>
}
