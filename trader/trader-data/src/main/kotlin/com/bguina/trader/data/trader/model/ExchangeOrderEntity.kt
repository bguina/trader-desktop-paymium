package com.bguina.trader.data.trader.model

import com.bguina.trader.data.mapper.ExchangeOrderOperationEntity
import com.bguina.trader.data.trader.TradeEntity
import java.util.*

data class ExchangeOrderEntity(
    val exchange: EExchangeEntity?,
    val id: String?,
    val createdAt: Date?,
    val updatedAt: Date?,
    val status: EStatus?,
    val expectedTrade: TradeEntity?,
    val executedTrade: TradeEntity?,
    val operations: List<ExchangeOrderOperationEntity>?
) {
    enum class EStatus {
        PENDING,
        ACTIVE,
        EXECUTED,
        CANCELED
    }
}
