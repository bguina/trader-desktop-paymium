package com.bguina.trader.domain.model

import java.util.*

data class ExchangeOrderOperation(
    val isFee: Boolean,
    val createdAt: Date,
    val asset: Asset
)
