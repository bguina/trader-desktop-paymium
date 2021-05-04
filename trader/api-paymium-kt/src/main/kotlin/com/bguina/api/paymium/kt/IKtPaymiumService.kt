package com.bguina.api.paymium.kt

import com.bguina.api.paymium.model.v1.countries.PaymiumCountryDTO
import com.bguina.api.paymium.model.v1.data.currency.ohlcv.PaymiumCurrencyOhlcvDTO
import com.bguina.api.paymium.model.v1.data.currency.ticker.PaymiumCurrencyTickerDTO
import com.bguina.api.paymium.model.v1.data.currency.ticker.PaymiumMarketDepthDTO
import com.bguina.api.paymium.model.v1.data.currency.trades.PaymiumCurrencyTradeDTO
import com.bguina.api.paymium.model.v1.user.PaymiumUserDTO
import com.bguina.api.paymium.model.v1.user.addresses.PaymiumCoinAddressDTO
import com.bguina.api.paymium.model.v1.user.orders.PaymiumUserOrderDTO
import retrofit2.http.*


interface IKtPaymiumService {

    @GET("v1/countries")
    suspend fun listCountries(
    ): List<PaymiumCountryDTO>

    /**
     * Read open high low close volume data
     * */
    @GET("v1/data/{currency}/ohlcv")
    suspend fun getCurrencyOhlcv(
        @Path("currency") currencyCode: String,
        @Query("startTime") startTimeEpoch: Long? = null,
        @Query("endTime") endTimeEpoch: Long? = null,
        @Query("interval") interval: String
    ): List<PaymiumCurrencyOhlcvDTO>

    /**
     * Read latest ticker data
     * https://paymium.github.io/api-documentation/#tag/Public-data/paths/~1data~1{currency}~1trades/get
     * */
    @GET("v1/data/{currency}/ticker")
    suspend fun getCurrencyTicker(
        @Path("currency") currencyCode: String
    ): PaymiumCurrencyTickerDTO


    /**
     * Read the latest executed trades
     * https://paymium.github.io/api-documentation/#tag/Public-data/paths/~1data~1{currency}~1trades/get
     * */
    @GET("v1/data/{currency}/trades")
    suspend fun listLatestTrades(
        @Path("currency") currency: String,
        @Query("since") sinceEpoch: Long? = null
    ): List<PaymiumCurrencyTradeDTO>

    /**
     * Read the market dept, bids and asks are grouped by price.
     * https://paymium.github.io/api-documentation/#tag/Public-data/paths/~1data~1{currency}~1depth/get
     * */
    @GET("v1/data/{currency}/depth")
    suspend fun getCurrencyMarketDepth(
        @Path("currency") currency: String
    ): PaymiumMarketDepthDTO

    /**
     * Read the latest user info.
     * */
    @GET("v1/user")
    suspend fun getUser(): PaymiumUserDTO

    /**
     * Retrieve your Bitcoin deposit addresses along with their expiration timestamp.
     * */
    @GET("v1/user/addresses")
    suspend fun getUserBitcoinDepositAddressList(
    ): List<PaymiumCoinAddressDTO>

    @Headers("Content-Type: application/json")
    @POST("v1/user/orders")
    suspend fun createUserOrder(
        /**
         * Enum: "LimitOrder", "MarketOrder"
         * */
        @Query("type") type: Int,
        @Query("currency") currencyCode: String,
        /**
         * Enum: "buy", "sell"
         * */
        @Query("direction") direction: String,
        /**
         * price per BTC, must be omitted for market orders
         * */
        @Query("price") price: Double?,
        /**
         * BTC amount to trade. Either one of amount or currency_amount must be specified. When the amount is specified, the engine will buy or sell this amount of Bitcoins. When the currency_amount is specified, the engine will buy as much Bitcoins as possible for currency_amount or sell as much Bitcoins as necessary to obtain currency_amount.
         * */
        @Query("amount") amount: Double?,
        /**
         * Currency amount to trade. Either one of amount or currency_amount must be specified. When the amount is specified, the engine will buy or sell this amount of Bitcoins. When the currency_amount is specified, the engine will buy as much Bitcoins as possible for currency_amount or sell as much Bitcoins as necessary to obtain currency_amount.
         * */
        @Query("currency_amount") currency_amount: Double?
    ): PaymiumUserOrderDTO

    @GET("v1/user/orders")
    suspend fun getUserOrderList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        /**
         * filter by types. Array of strings. Items Enum: "LimitOrder", "MarketOrder", "BitcoinDeposit", "WireDeposit", "Payment", "EmailTransfer", "EmailDeposit"
         * */
        @Query("types") types: String? = null,
        /**
         * only show active orders
         * */
        @Query("active") active: Boolean? = null
    ): List<PaymiumUserOrderDTO>

    @GET("v1/user/orders/{uuid}")
    suspend fun getUserOrder(
        @Path("uuid") uuid: String
    ): PaymiumUserOrderDTO

    @DELETE("v1/user/orders/{uuid}")
    suspend fun deleteUserOrder(
        @Path("uuid") uuid: String
    )
}
