package com.bguina.api.paymium.model.v1

enum class EPaymiumV1Interval(
    val identifier: String
) {
    ONE_MINUTE("1m"),
    THREE_MINUTE("3m"),
    FIVE_MINUTE("5m"),
    FIFTEEN_MINUTE("15m"),
    THIRTY_MINUTE("30m"),
    ONE_HOUR("1h"),
    TWO_HOURS("2h"),
    FOUR_HOURS("4h"),
    SIX_HOURS("6h"),
    HEIGHT_HOURS("8h"),
    TWELVE_HOURS("12h"),
    ONE_DAY("1d"),
    THREE_DAYS("3d"),
    ONE_WEEK("1w"),
    ONE_MONTH("1M"),
}
