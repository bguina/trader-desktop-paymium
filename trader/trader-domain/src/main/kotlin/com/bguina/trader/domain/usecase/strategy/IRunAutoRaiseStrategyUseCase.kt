package com.bguina.trader.domain.usecase.strategy

import com.bguina.trader.domain.model.Asset
import com.bguina.trader.domain.model.EExchange
import com.bguina.trader.domain.model.EMarket
import io.reactivex.Flowable

interface IRunAutoRaiseStrategyUseCase {
    fun runAutoRaise(
        exchange: EExchange,
        market: EMarket,
        direction: EMarket.EDirection,
        initialOffer: Asset,
        limit: Double?
    ): Flowable<Double>
}
