package com.bguina.api.paymium.kt

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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path

/**
 * Reference: https://paymium.github.io/api-documentation/
 * */
class KtPaymiumApiClient(
    apiKey: String,
    secret: String,
    baseHttpClient: OkHttpClient = OkHttpClient.Builder().build(),
    private val networkDispatcher: CoroutineDispatcher = Dispatchers.IO,
    interceptors: List<Interceptor> = emptyList()
) {
    private val v1Service: IKtPaymiumService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .client(
            baseHttpClient.newBuilder()
                .addInterceptor(
                    PaymiumAuthTokenInterceptor(
                        apiKey = apiKey,
                        secret = secret
                    )
                )
                .apply { interceptors.forEach(::addInterceptor) }
                .build()
        )
        .baseUrl(PaymiumConstants.PAYMIUM_API_BASE_URL)
        .build()
        .create(IKtPaymiumService::class.java)

    suspend fun listAcceptedCountries(
    ): List<PaymiumCountryDTO> = withContext(networkDispatcher) {
        v1Service.listCountries()
            .filter { true == it.accepted }
    }

    suspend fun getCurrencyOhlcv(
        currency: EPaymiumV1Currency,
        startTimeEpoch: Long?,
        endTimeEpoch: Long?,
        interval: EPaymiumV1Interval
    ): List<PaymiumCurrencyOhlcvDTO> = withContext(networkDispatcher) {
        v1Service.getCurrencyOhlcv(
            currencyCode = currency.identifier,
            startTimeEpoch = startTimeEpoch,
            endTimeEpoch = endTimeEpoch,
            interval = interval.identifier
        )
    }

    suspend fun getBitcoinEuroTicker(
    ): PaymiumCurrencyTickerDTO = withContext(networkDispatcher) {
        v1Service.getCurrencyTicker(
            currencyCode = EPaymiumV1Currency.EUR.identifier
        )
    }

    suspend fun listLatestTrades(
        currency: EPaymiumV1Currency,
        sinceEpoch: Long
    ): List<PaymiumCurrencyTradeDTO> = withContext(networkDispatcher) {
        v1Service.listLatestTrades(
            currency = currency.identifier,
            sinceEpoch = sinceEpoch
        )
    }

    suspend fun getCurrencyMarketDepth(
        currency: EPaymiumV1Currency
    ): PaymiumMarketDepthDTO = withContext(networkDispatcher) {
        v1Service.getCurrencyMarketDepth(
            currency = currency.identifier
        )
    }

    suspend fun getUser(
    ): PaymiumUserDTO = withContext(networkDispatcher) {
        v1Service.getUser()
    }


    suspend fun listUserBitcoinDepositAddresses(
    ): List<PaymiumCoinAddressDTO> = withContext(networkDispatcher) {
        v1Service.getUserBitcoinDepositAddressList()
    }

    suspend fun listUserOrders(
        offset: Int,
        limit: Int,
        types: Set<EOrderType>? = null,
        active: Boolean? = null,
    ): List<PaymiumUserOrderDTO> = withContext(networkDispatcher) {
        v1Service.getUserOrderList(
            offset = offset,
            limit = limit,
            types = types?.run { EOrderType.toString(this) },
            active = active
        )
    }
}
