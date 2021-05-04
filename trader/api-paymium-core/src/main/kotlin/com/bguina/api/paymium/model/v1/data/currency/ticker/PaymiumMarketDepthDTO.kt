package com.bguina.api.paymium.model.v1.data.currency.ticker

data class PaymiumMarketDepthDTO(
    val asks: List<PaymiumMarketDepthOrderDTO>? = null,
    val bids: List<PaymiumMarketDepthOrderDTO>? = null
)
