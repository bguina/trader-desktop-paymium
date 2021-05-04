package com.bguina.trader.data.trader

import com.bguina.trader.data.trader.model.*
import io.reactivex.Completable
import io.reactivex.Single
import java.time.chrono.ChronoPeriod

interface IExchangeRemoteDataSource {
    fun getExchange(
        account: ExchangeAccountEntity
    ): Single<ExchangeEntity>

    fun listAssets(
        assets: Set<EAssetEntity>
    ): Single<Map<EAssetEntity, Double>>

    fun getAssetValue(
        market: EMarketEntity,
        asset: AssetEntity
    ): Single<AssetEntity>

    fun listAccountOrders(
        account: ExchangeAccountEntity,
        period: ChronoPeriod? = null,
        status: Set<ExchangeOrderEntity.EStatus>? = null
    ): Single<List<ExchangeOrderEntity>>

    fun submitTradeOrder(
        market: EMarketEntity,
        buy: Boolean,
        amount: Double,
        limit: Double?
    ): Single<ExchangeOrderEntity>

    fun cancelOrder(
        orderId: String
    ): Completable
}
