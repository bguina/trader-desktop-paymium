package com.bguina.api.paymium.rx2

import com.bguina.api.paymium.IPaymium
import com.bguina.api.paymium.model.v1.EPaymiumV1Currency
import com.bguina.api.paymium.model.v1.EPaymiumV1Interval
import com.bguina.api.paymium.model.v1.countries.PaymiumCountryDTO
import com.bguina.api.paymium.model.v1.data.currency.ohlcv.PaymiumCurrencyOhlcvDTO
import com.bguina.api.paymium.model.v1.data.currency.ticker.PaymiumCurrencyTickerDTO
import com.bguina.api.paymium.model.v1.data.currency.ticker.PaymiumMarketDepthDTO
import com.bguina.api.paymium.model.v1.data.currency.trades.PaymiumCurrencyTradeDTO
import com.bguina.api.paymium.model.v1.user.PaymiumUserDTO
import com.bguina.api.paymium.model.v1.user.addresses.PaymiumCoinAddressDTO
import com.bguina.api.paymium.model.v1.user.orders.PaymiumUserOrderDTO
import com.bguina.trader.util.interceptor.Log4jOkHttpLoggingInterceptor
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager

class Rx2Paymium(
    apiKey: String,
    secret: String
) : IPaymium {

    private val apiClient: Rx2PaymiumApiClient = Rx2PaymiumApiClient(
        apiKey = apiKey,
        secret = secret,
        networkScheduler = Schedulers.trampoline()
    )

    override fun listAcceptedCountries(
        cb: (countryDtos: List<PaymiumCountryDTO>) -> Unit
    ) {
        apiClient.listAcceptedCountries()
            .observeOn(Schedulers.trampoline())
            .subscribe(cb) { throw it }
    }

    override fun getCurrencyOhlcv(
        currency: EPaymiumV1Currency,
        startTimeEpoch: Long?,
        endTimeEpoch: Long?,
        interval: EPaymiumV1Interval,
        cb: (List<PaymiumCurrencyOhlcvDTO>) -> Unit
    ) {
        apiClient.getCurrencyOhlcv(
            currency = currency,
            startTimeEpoch = startTimeEpoch,
            endTimeEpoch = endTimeEpoch,
            interval = interval
        )
            .observeOn(Schedulers.trampoline())
            .subscribe(cb) { throw it }
    }

    override fun getBitcoinTickerInEuro(
        cb: (PaymiumCurrencyTickerDTO) -> Unit
    ) {
        apiClient.getTicker()
            .observeOn(Schedulers.trampoline())
            .subscribe(cb) { throw it }
    }

    override fun listLatestTrades(
        currency: EPaymiumV1Currency,
        sinceEpoch: Long,
        cb: (latestTradeListDto: List<PaymiumCurrencyTradeDTO>) -> Unit
    ) {
        apiClient.getLatestTradeList(
            currency = currency,
            sinceEpoch = sinceEpoch
        )
            .observeOn(Schedulers.trampoline())
            .subscribe(cb) { throw it }
    }

    override fun getMarketDepth(
        currency: EPaymiumV1Currency,
        cb: (marketDepthDto: PaymiumMarketDepthDTO) -> Unit
    ) {
        apiClient.getCurrencyMarketDepth(currency = currency)
            .observeOn(Schedulers.trampoline())
            .subscribe(cb) { throw it }
    }

    override fun getUser(
        cb: (PaymiumUserDTO) -> Unit
    ) {
        apiClient.privateGetUser()
            .observeOn(Schedulers.trampoline())
            .subscribe(cb) { throw it }
    }

    override fun listUserBitcoinDepositAddresses(
        cb: (btcDepositAddressList: List<PaymiumCoinAddressDTO>) -> Unit
    ) {
        cb(
            apiClient.privateListUserBitcoinDepositAddresses()
                .observeOn(Schedulers.trampoline())
                .blockingGet()
        )
    }

    override fun listUserOrders(
        offset: Int,
        limit: Int,
        cb: (orders: List<PaymiumUserOrderDTO>) -> Unit
    ) {
        apiClient.privateListUserOrders(
            offset = offset,
            limit = limit
        )
            .observeOn(Schedulers.trampoline())
            .subscribe(cb) { throw it }
    }
}
