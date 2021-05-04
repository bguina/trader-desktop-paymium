package com.bguina.trader.data.di

import com.bguina.trader.data.mapper.*
import com.bguina.trader.data.mapper.base.IBiMapper
import com.bguina.trader.data.trader.TradeEntity
import com.bguina.trader.data.trader.model.*
import com.bguina.trader.domain.model.*
import dagger.Binds
import dagger.Module

@Module
interface TraderMappersModule {
    @Binds
    fun bindEExchangeMapper(impl: EExchangeMapper): IBiMapper<EExchangeEntity, EExchange>

    @Binds
    fun bindExchangeMapper(impl: ExchangeMapper): IBiMapper<ExchangeEntity, ExchangeStatus>

    @Binds
    fun bindMarketMapper(impl: EMarketMapper): IBiMapper<EMarketEntity, EMarket>

    @Binds
    fun bindExchangeAccountMapper(impl: ExchangeAccountMapper): IBiMapper<ExchangeAccountEntity, ExchangeAccount>

    @Binds
    fun bindCurrencyMapper(impl: CurrencyMapper): IBiMapper<EAssetEntity, EAsset>

    @Binds
    fun bindAssetMapper(impl: AssetMapper): IBiMapper<AssetEntity, Asset>

    @Binds
    fun bindExchangeOrderStatusMapper(impl: ExchangeOrderStatusMapper): IBiMapper<ExchangeOrderEntity.EStatus, ExchangeOrder.EStatus>

    @Binds
    fun bindTradeMapper(impl: TradeMapper): IBiMapper<TradeEntity, Trade>

    @Binds
    fun bindExchangeOrderMapper(impl: ExchangeOrderMapper): IBiMapper<ExchangeOrderEntity, ExchangeOrder>

    @Binds
    fun bindExchangeOrderOperationMapper(impl: ExchangeOrderOperationMapper): IBiMapper<ExchangeOrderOperationEntity, ExchangeOrderOperation>

}
