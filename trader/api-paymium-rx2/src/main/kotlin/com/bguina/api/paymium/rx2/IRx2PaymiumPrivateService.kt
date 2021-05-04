package com.bguina.api.paymium.rx2

import com.bguina.api.paymium.model.v1.user.PaymiumUserDTO
import com.bguina.api.paymium.model.v1.user.addresses.PaymiumCoinAddressDTO
import com.bguina.api.paymium.model.v1.user.orders.PaymiumUserOrderDTO
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

/**
 * Reference: https://paymium.github.io/api-documentation/
 * */
interface IRx2PaymiumPrivateService {
    /**
     * Read the latest user info.
     * */
    @GET("v1/user")
    fun getUser(): Single<PaymiumUserDTO>

    /**
     * Retrieve your Bitcoin deposit addresses along with their expiration timestamp.
     * */
    @GET("v1/user/addresses")
    fun getUserBitcoinDepositAddressList(
    ): Single<List<PaymiumCoinAddressDTO>>

    @Headers("Content-Type: application/json")
    @POST("v1/user/orders")
    fun postUserOrder(
        /**
         * Enum: "LimitOrder", "MarketOrder"
         * */
        @Query("type") type: String,
        @Query("currency") currency: String,
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
    ): Single<PaymiumUserOrderDTO>

    @GET("v1/user/orders")
    fun getUserOrderList(
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
    ): Single<List<PaymiumUserOrderDTO>>

    @GET("v1/user/orders/{uuid}")
    fun getUserOrder(
        @Path("uuid") uuid: String
    ): Single<PaymiumUserOrderDTO>

    @DELETE("v1/user/orders/{uuid}")
    fun deleteUserOrder(
        @Path("uuid") uuid: String
    ): Completable
}
