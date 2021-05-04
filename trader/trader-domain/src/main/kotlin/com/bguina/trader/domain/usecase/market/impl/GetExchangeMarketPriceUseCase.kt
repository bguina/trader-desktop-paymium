package com.bguina.trader.domain.usecase.market.impl

import com.bguina.trader.domain.IExchangeRepository
import com.bguina.trader.domain.di.TraderNamedDependencies
import com.bguina.trader.domain.model.Asset
import com.bguina.trader.domain.model.EExchange
import com.bguina.trader.domain.model.EMarket
import com.bguina.trader.domain.usecase.market.IGetExchangeMarketPriceUseCase
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class GetExchangeMarketPriceUseCase @Inject constructor(
    @Named(TraderNamedDependencies.SCHEDULER_UI) private val appScheduler: Scheduler,
    private val exchangeRepository: IExchangeRepository
) : IGetExchangeMarketPriceUseCase {
    override fun getMarketPrice(
        exchange: EExchange,
        market: EMarket
    ): Single<Asset> = exchangeRepository.getAssetPrice(
        exchanges = setOf(exchange),
        market = market,
        asset = Asset(amount = 1.0, currency = market.stock)
    )
        .map { it.values.single() }
        .observeOn(appScheduler)
}
