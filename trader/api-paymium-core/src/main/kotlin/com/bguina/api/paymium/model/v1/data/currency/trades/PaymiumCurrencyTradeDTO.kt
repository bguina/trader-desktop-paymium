package com.bguina.api.paymium.model.v1.data.currency.trades

data class PaymiumCurrencyTradeDTO(
    val created_at: String? = null,
    val created_at_int: Int? = null,
    val currency: String? = null,
    val price: String? = null,
    val traded_btc: String? = null,
    val traded_currency: String? = null,
    val uuid: String? = null
)
