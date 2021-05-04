package com.bguina.trader.di

import com.bguina.trader.data.di.TraderDataModule
import com.bguina.trader.data.local.di.TraderDataLocalModule
import com.bguina.trader.data.remote.di.BaseHttpModule
import com.bguina.trader.data.remote.di.PaymiumExchangeDataRemoteModule
import com.bguina.trader.domain.di.TraderDomainModule
import com.bguina.trader.ui.account_orders.AccountOrderListController
import com.bguina.trader.ui.MainController
import com.bguina.trader.ui.market_strategy.MarketStrategyController
import com.bguina.trader.ui.exchange_market.ExchangeMarketController
import com.bguina.trader.ui.account_assets.AccountAssetListController
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(
    includes = [
        BaseHttpModule::class,
        PaymiumExchangeDataRemoteModule::class,
        TraderDataLocalModule::class,
        TraderDataModule::class,
        TraderDomainModule::class
    ]
)
interface TraderModule {
    @[Binds IntoMap ControllerKey(MainController::class)]
    fun bindMainController(controller: MainController): Any

    @[Binds IntoMap ControllerKey(AccountAssetListController::class)]
    fun bindUserAssetsController(controller: AccountAssetListController): Any

    @[Binds IntoMap ControllerKey(ExchangeMarketController::class)]
    fun bindExchangeTickerController(controller: ExchangeMarketController): Any

    @[Binds IntoMap ControllerKey(MarketStrategyController::class)]
    fun bindMarketStrategyController(controller: MarketStrategyController): Any

    @[Binds IntoMap ControllerKey(AccountOrderListController::class)]
    fun bindHistoryController(controller: AccountOrderListController): Any
}
