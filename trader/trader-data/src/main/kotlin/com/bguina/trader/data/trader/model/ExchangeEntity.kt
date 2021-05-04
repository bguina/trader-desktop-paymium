package com.bguina.trader.data.trader.model

data class ExchangeEntity(
    val which: EExchangeEntity,
    val name: String,
    val status: EStatus = EStatus.UNKNOWN,
) {
    enum class EStatus{
        UNKNOWN,
        UNAVAILABLE,
        API_QUOTA_REACHED,
        ORDER_QUOTA_REACHED,
        READY
    }
}
