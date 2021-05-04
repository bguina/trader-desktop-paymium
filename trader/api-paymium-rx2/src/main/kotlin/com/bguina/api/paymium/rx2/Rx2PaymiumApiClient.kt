package com.bguina.api.paymium.rx2

import com.bguina.api.paymium.PaymiumConstants
import com.bguina.api.paymium.auth.PaymiumAuthTokenInterceptor
import com.bguina.api.paymium.model.v1.EPaymiumV1Currency
import com.bguina.api.paymium.model.v1.EPaymiumV1Interval
import com.bguina.api.paymium.model.v1.countries.PaymiumCountryDTO
import com.bguina.api.paymium.model.v1.data.currency.ohlcv.PaymiumCurrencyOhlcvDTO
import com.bguina.api.paymium.model.v1.data.currency.ticker.PaymiumCurrencyTickerDTO
import com.bguina.api.paymium.model.v1.data.currency.ticker.PaymiumMarketDepthDTO
import com.bguina.api.paymium.model.v1.data.currency.trades.PaymiumCurrencyTradeDTO
import com.bguina.api.paymium.model.v1.user.PaymiumUserDTO
import com.bguina.api.paymium.model.v1.user.addresses.PaymiumCoinAddressDTO
import com.bguina.api.paymium.model.v1.user.orders.EOrderType
import com.bguina.api.paymium.model.v1.user.orders.PaymiumUserOrderDTO
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Reference: https://paymium.github.io/api-documentation/
 * */
class Rx2PaymiumApiClient(
    apiKey: String,
    secret: String,
    baseHttpClient: OkHttpClient = OkHttpClient.Builder().build(),
    private val networkScheduler: Scheduler = Schedulers.io()
) {
    private val v1PublicService: IRx2PaymiumPublicService = baseHttpClient
            .makeService(IRx2PaymiumPublicService::class.java)

    private val authTokenInterceptor: PaymiumAuthTokenInterceptor = PaymiumAuthTokenInterceptor(
        apiKey = apiKey,
        secret = secret
    )

    private val v1PrivateService: IRx2PaymiumPrivateService = baseHttpClient.newBuilder()
            .addInterceptor(authTokenInterceptor)
            .build()
            .makeService(IRx2PaymiumPrivateService::class.java)



    fun listAcceptedCountries(
    ): Single<List<PaymiumCountryDTO>> = v1PublicService.getCountryList()
        .map { countryList -> countryList.filter { true == it.accepted } }
        .subscribeOn(networkScheduler)

    fun getCurrencyOhlcv(
        currency: EPaymiumV1Currency,
        startTimeEpoch: Long?,
        endTimeEpoch: Long?,
        interval: EPaymiumV1Interval
    ): Single<List<PaymiumCurrencyOhlcvDTO>> = v1PublicService.getCurrencyOhlcv(
        currency = currency.identifier,
        startTimeEpoch = startTimeEpoch,
        endTimeEpoch = endTimeEpoch,
        interval = interval.identifier
    )
        .subscribeOn(networkScheduler)

    fun getTicker(
    ): Single<PaymiumCurrencyTickerDTO> = v1PublicService.getCurrencyTicker(
        currency = EPaymiumV1Currency.EUR.identifier
    )
        .subscribeOn(networkScheduler)

    fun getLatestTradeList(
        currency: EPaymiumV1Currency,
        sinceEpoch: Long
    ): Single<List<PaymiumCurrencyTradeDTO>> = v1PublicService.getLatestTradeList(
        currency = currency.identifier,
        sinceEpoch = sinceEpoch
    )
        .subscribeOn(networkScheduler)

    fun getCurrencyMarketDepth(
        currency: EPaymiumV1Currency
    ): Single<PaymiumMarketDepthDTO> = v1PublicService.getCurrencyMarketDepth(
        currency = currency.identifier
    )
        .subscribeOn(networkScheduler)

    fun privateGetUser(
    ): Single<PaymiumUserDTO> = v1PrivateService.getUser()
        .subscribeOn(networkScheduler)

    fun privateListUserBitcoinDepositAddresses(
    ): Single<List<PaymiumCoinAddressDTO>> = v1PrivateService.getUserBitcoinDepositAddressList()
        .subscribeOn(networkScheduler)

    fun privateListUserOrders(
        offset: Int,
        limit: Int,
        types: Set<EOrderType>? = null,
        active: Boolean? = null,
    ): Single<List<PaymiumUserOrderDTO>> = v1PrivateService.getUserOrderList(
        offset = offset,
        limit = limit,
        types = types?.run { EOrderType.toString(this) },
        active = active
    )
        .subscribeOn(networkScheduler)

    fun privatePostOrder(
        buy: Boolean,
        btcAmount: Double?,
        limitPrice: Double? = null
    ): Single<PaymiumUserOrderDTO> = v1PrivateService.postUserOrder(
        type = EOrderType.LimitOrder.identifier,
        currency = "EUR",
        direction = if (buy) PaymiumUserOrderDTO.ORDER_DIRECTION_BUY
        else PaymiumUserOrderDTO.ORDER_DIRECTION_SELL,
        price = limitPrice,
        amount = btcAmount,
        currency_amount = null
    )
        .subscribeOn(networkScheduler)

    fun privateDeleteUserOrderMarketOrder(
        orderId: String
    ): Completable = v1PrivateService.deleteUserOrder(
        uuid = orderId
    )
        .subscribeOn(networkScheduler)

    private fun <T> OkHttpClient.makeService(
        service: Class<T>
    ): T = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(this)
        .baseUrl(PaymiumConstants.PAYMIUM_API_BASE_URL)
        .build()
        .create(service)
}
