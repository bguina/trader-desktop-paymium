package com.bguina.trader.domain.usecase.account.impl

import com.bguina.trader.domain.IExchangeRepository
import com.bguina.trader.domain.di.TraderNamedDependencies
import com.bguina.trader.domain.model.Asset
import com.bguina.trader.domain.model.EAsset
import com.bguina.trader.domain.model.EExchange
import com.bguina.trader.domain.usecase.account.IListExchangeAccountAssetsUseCase
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class ListExchangeAccountAssetsUseCase @Inject constructor(
    @Named(TraderNamedDependencies.SCHEDULER_UI) private val appScheduler: Scheduler,
    private val exchangeRepository: IExchangeRepository
) : IListExchangeAccountAssetsUseCase {
    override fun listAssets(
        exchange: EExchange
    ): Single<List<Asset>> = exchangeRepository.listAssets(
        exchange = exchange,
        assets = EAsset.values().toSet()
    )
        .map { assetMap ->
            assetMap
                .map { Asset(amount = it.value, currency = it.key) }
                .filterNot { it.depleted }
                .sortedBy { it.currency.ordinal }
        }
        .observeOn(appScheduler)
}
