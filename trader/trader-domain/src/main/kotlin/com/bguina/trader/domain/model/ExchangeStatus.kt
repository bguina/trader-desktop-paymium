package com.bguina.trader.domain.model

data class ExchangeStatus(
    val which: EExchange,
    val name: String,
    val status: EStatus = EStatus.UNKNOWN
) {
    enum class EStatus {
        UNKNOWN,
        UNAVAILABLE,
        API_QUOTA_REACHED,
        ORDER_QUOTA_REACHED,
        READY
    }
}
