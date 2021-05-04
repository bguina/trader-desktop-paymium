package com.bguina.api.paymium

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

interface IPaymium {

    fun listAcceptedCountries(
        cb: (countryDtos: List<PaymiumCountryDTO>) -> Unit
    )

    fun getCurrencyOhlcv(
        currency: EPaymiumV1Currency,
        startTimeEpoch: Long? = null,
        endTimeEpoch: Long? = null,
        interval: EPaymiumV1Interval,
        cb: (currencyOhlcvDto: List<PaymiumCurrencyOhlcvDTO>) -> Unit
    )

    fun getBitcoinTickerInEuro(
        cb: (currencyTickerDto: PaymiumCurrencyTickerDTO) -> Unit
    )

    fun listLatestTrades(
        currency: EPaymiumV1Currency,
        sinceEpoch: Long,
        cb: (latestTradeListDto: List<PaymiumCurrencyTradeDTO>) -> Unit
    )

    fun getMarketDepth(
        currency: EPaymiumV1Currency,
        cb: (marketDepthDto: PaymiumMarketDepthDTO) -> Unit
    )

    fun getUser(
        cb: (userDto: PaymiumUserDTO) -> Unit
    )

    fun listUserBitcoinDepositAddresses(
        cb: (btcDepositAddressList: List<PaymiumCoinAddressDTO>) -> Unit
    )

    fun listUserOrders(
        offset: Int,
        limit: Int,
        cb: (orders: List<PaymiumUserOrderDTO>) -> Unit
    )
}
