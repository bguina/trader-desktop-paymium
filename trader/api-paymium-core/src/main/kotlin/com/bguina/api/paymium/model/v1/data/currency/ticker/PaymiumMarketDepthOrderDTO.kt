package com.bguina.api.paymium.model.v1.data.currency.ticker

data class PaymiumMarketDepthOrderDTO(
    val amount: String? = null,
    val currency: String? = null,
    val price: String? = null,
    val timestamp: Int? = null
)
