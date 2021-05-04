package com.bguina.trader.domain.usecase.order

import com.bguina.trader.domain.model.EExchange
import com.bguina.trader.domain.model.EMarket
import com.bguina.trader.domain.model.ExchangeOrder
import io.reactivex.Single

interface ISubmitTradeOrderUseCase {
    fun submitTradeOrder(
        exchange: EExchange,
        market: EMarket,
        direction: EMarket.EDirection,
        amount: Double,
        limit: Double?
    ) : Single<ExchangeOrder>
}
