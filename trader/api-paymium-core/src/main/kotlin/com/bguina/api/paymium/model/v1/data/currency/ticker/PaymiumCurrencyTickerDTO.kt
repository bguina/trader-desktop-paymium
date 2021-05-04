package com.bguina.api.paymium.model.v1.data.currency.ticker

data class PaymiumCurrencyTickerDTO(
    val ask: String? = null,
    val at: Int? = null,
    val bid: String? = null,
    val currency: String? = null,
    val high: String? = null,
    val low: String? = null,
    val midpoint: String? = null,
    val price: String? = null,
    val variation: String? = null,
    val volume: String? = null
)
