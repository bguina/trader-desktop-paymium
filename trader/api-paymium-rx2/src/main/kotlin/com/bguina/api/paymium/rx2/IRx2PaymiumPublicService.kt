package com.bguina.api.paymium.rx2

import com.bguina.api.paymium.model.v1.countries.PaymiumCountryDTO
import com.bguina.api.paymium.model.v1.data.currency.ohlcv.PaymiumCurrencyOhlcvDTO
import com.bguina.api.paymium.model.v1.data.currency.ticker.PaymiumCurrencyTickerDTO
import com.bguina.api.paymium.model.v1.data.currency.ticker.PaymiumMarketDepthDTO
import com.bguina.api.paymium.model.v1.data.currency.trades.PaymiumCurrencyTradeDTO
import com.bguina.api.paymium.model.v1.user.PaymiumUserDTO
import com.bguina.api.paymium.model.v1.user.addresses.PaymiumCoinAddressDTO
import com.bguina.api.paymium.model.v1.user.orders.PaymiumUserOrderDTO
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

/**
 * Reference: https://paymium.github.io/api-documentation/
 * */
interface IRx2PaymiumPublicService {
    /**
     * Public
     * Get list of countries
     * */
    @GET("v1/countries")
    fun getCountryList(
    ): Single<List<PaymiumCountryDTO>>

    /**
     * Public
     * Read open high low close volume data
     * */
    @GET("v1/data/{currency}/ohlcv")
    fun getCurrencyOhlcv(
        @Path("currency") currency: String,
        @Query("startTime") startTimeEpoch: Long? = null,
        @Query("endTime") endTimeEpoch: Long? = null,
        @Query("interval") interval: String
    ): Single<List<PaymiumCurrencyOhlcvDTO>>

    /**
     * Public
     * Read latest ticker data
     * */
    @GET("v1/data/{currency}/ticker")
    fun getCurrencyTicker(
        @Path("currency") currency: String
    ): Single<PaymiumCurrencyTickerDTO>

    /**
     * Public
     * Read the latest executed trades
     * https://paymium.github.io/api-documentation/#tag/Public-data/paths/~1data~1{currency}~1trades/get
     * */
    @GET("v1/data/{currency}/trades")
    fun getLatestTradeList(
        @Path("currency") currency: String,
        @Query("since") sinceEpoch: Long? = null
    ): Single<List<PaymiumCurrencyTradeDTO>>

    /**
     * Public
     * Read the market dept, bids and asks are grouped by price.
     * https://paymium.github.io/api-documentation/#tag/Public-data/paths/~1data~1{currency}~1depth/get
     * */
    @GET("v1/data/{currency}/depth")
    fun getCurrencyMarketDepth(
        @Path("currency") currency: String
    ): Single<PaymiumMarketDepthDTO>

}
