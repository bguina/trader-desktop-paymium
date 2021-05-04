package com.bguina.trader.domain

import com.bguina.trader.domain.model.*
import io.reactivex.Completable
import io.reactivex.Single
import java.time.chrono.ChronoPeriod

interface IExchangeRepository {
    fun getTrader(
        account: ExchangeAccount,
        exchange: EExchange
    ): Single<ExchangeStatus>

    fun listAssets(
        exchange: EExchange,
        assets: Set<EAsset>
    ): Single<Map<EAsset, Double>>

    fun getAsset(
        userId: Long,
        exchange: EExchange,
        asset: EAsset
    ): Single<Double>

    fun submitTradeOrder(
        exchange: EExchange,
        market: EMarket,
        direction: EMarket.EDirection,
        amount: Double,
        limit: Double?
    ): Single<ExchangeOrder>

    fun listOrders(
        account: ExchangeAccount,
        period: ChronoPeriod? = null,
        status: ExchangeOrder.EStatus? = null
    ): Single<List<ExchangeOrder>>

    fun cancelOrder(
        account: ExchangeAccount,
        orderId: String
    ): Completable

    fun getAssetPrice(
        exchanges: Set<EExchange>,
        market: EMarket,
        asset: Asset
    ):  Single<Map<EExchange, Asset>>

    fun getApplicableFee(
        account: ExchangeAccount,
        exchange: EExchange,
        order: ExchangeOrder
    ): Single<Asset>
}
