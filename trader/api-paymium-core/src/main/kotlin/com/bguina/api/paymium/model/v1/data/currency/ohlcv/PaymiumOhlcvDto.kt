package com.bguina.api.paymium.model.v1.data.currency.ohlcv

import com.google.gson.JsonPrimitive

typealias PaymiumCurrencyOhlcvDTO = List<JsonPrimitive>

val PaymiumCurrencyOhlcvDTO.timestamp: Long?
    get() = getOrNull(0)?.asLong

val PaymiumCurrencyOhlcvDTO.open: Float?
    get() = getOrNull(1)?.asFloat

val PaymiumCurrencyOhlcvDTO.high: Float?
    get() = getOrNull(2)?.asFloat

val PaymiumCurrencyOhlcvDTO.low: Float?
    get() = getOrNull(3)?.asFloat

val PaymiumCurrencyOhlcvDTO.close: Float?
    get() = getOrNull(4)?.asFloat

val PaymiumCurrencyOhlcvDTO.volume: Float?
    get() = getOrNull(5)?.asFloat
