package com.bguina.trader.domain.di

import com.bguina.trader.domain.usecase.account.IListExchangeAccountAssetsUseCase
import com.bguina.trader.domain.usecase.account.impl.ListExchangeAccountAssetsUseCase
import com.bguina.trader.domain.usecase.market.IGetExchangeMarketPriceUseCase
import com.bguina.trader.domain.usecase.market.impl.GetExchangeMarketPriceUseCase
import com.bguina.trader.domain.usecase.order.IListExchangeAccountOrdersUseCase
import com.bguina.trader.domain.usecase.order.ISubmitTradeOrderUseCase
import com.bguina.trader.domain.usecase.order.impl.ListExchangeAccountOrdersUseCase
import com.bguina.trader.domain.usecase.order.impl.SubmitTradeOrderUseCase
import com.bguina.trader.domain.usecase.strategy.IRunAutoRaiseStrategyUseCase
import com.bguina.trader.domain.usecase.strategy.impl.RunAutoRaiseStrategyUseCase
import dagger.Binds
import dagger.Module

@Module
interface TraderDomainModule {
    @Binds
    fun bindListExchangeAccountAssetsUseCase(
        impl: ListExchangeAccountAssetsUseCase
    ): IListExchangeAccountAssetsUseCase

    @Binds
    fun bindGetExchangeMarketPriceUseCase(
        impl: GetExchangeMarketPriceUseCase
    ): IGetExchangeMarketPriceUseCase

    @Binds
    fun bindListExchangeAccountOrdersUseCase(
        impl: ListExchangeAccountOrdersUseCase
    ): IListExchangeAccountOrdersUseCase

    @Binds
    fun bindSubmitTradeOrderUseCase(
        impl: SubmitTradeOrderUseCase
    ): ISubmitTradeOrderUseCase

    @Binds
    fun bindRunAutoRaiseStrategyUseCase(
        impl: RunAutoRaiseStrategyUseCase
    ): IRunAutoRaiseStrategyUseCase
}
