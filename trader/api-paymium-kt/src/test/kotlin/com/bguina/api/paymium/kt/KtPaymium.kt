package com.bguina.api.paymium.kt

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
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager

class KtPaymium(
    apiKey: String,
    secret: String
) : IPaymium {
    private val ktApiClient: KtPaymiumApiClient = KtPaymiumApiClient(
        apiKey = apiKey,
        secret = secret,
        interceptors = listOf(
            Log4jOkHttpLoggingInterceptor(
                logger = LogManager.getLogger(KtPaymium::class.java),
                logLevel = Level.INFO
            )
        )
    )

    override fun listAcceptedCountries(
        cb: (countryDtos: List<PaymiumCountryDTO>) -> Unit
    ) {
        runBlocking {
            cb(ktApiClient.listAcceptedCountries())
        }
    }

    override fun getCurrencyOhlcv(
        currency: EPaymiumV1Currency,
        startTimeEpoch: Long?,
        endTimeEpoch: Long?,
        interval: EPaymiumV1Interval,
        cb: (List<PaymiumCurrencyOhlcvDTO>) -> Unit
    ) {
        runBlocking {
            cb(
                ktApiClient.getCurrencyOhlcv(
                    currency = currency,
                    startTimeEpoch = startTimeEpoch,
                    endTimeEpoch = endTimeEpoch,
                    interval = interval
                )
            )
        }
    }

    override fun getBitcoinTickerInEuro(
        cb: (PaymiumCurrencyTickerDTO) -> Unit
    ) {
        runBlocking {
            cb(ktApiClient.getBitcoinEuroTicker())
        }
    }

    override fun getMarketDepth(
        currency: EPaymiumV1Currency,
        cb: (marketDepthDto: PaymiumMarketDepthDTO) -> Unit
    ) {
        runBlocking {
            cb(ktApiClient.getCurrencyMarketDepth(currency = currency))
        }
    }

    override fun getUser(
        cb: (PaymiumUserDTO) -> Unit
    ) {
        runBlocking {
            cb(ktApiClient.getUser())
        }
    }

    override fun listUserBitcoinDepositAddresses(
        cb: (btcDepositAddressList: List<PaymiumCoinAddressDTO>) -> Unit
    ) {
        runBlocking {
            cb(ktApiClient.listUserBitcoinDepositAddresses())
        }
    }

    override fun listUserOrders(
        offset: Int,
        limit: Int,
        cb: (orders: List<PaymiumUserOrderDTO>) -> Unit
    ) {
        runBlocking {
            cb(
                ktApiClient.listUserOrders(
                    offset = offset,
                    limit = limit
                )
            )
        }
    }

    override fun listLatestTrades(
        currency: EPaymiumV1Currency,
        sinceEpoch: Long,
        cb: (latestTradeListDto: List<PaymiumCurrencyTradeDTO>) -> Unit
    ) {
        runBlocking {
            cb(
                ktApiClient.listLatestTrades(
                    currency = currency,
                    sinceEpoch = sinceEpoch
                )
            )
        }
    }
}
