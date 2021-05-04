package com.bguina.trader.domain.model

import java.util.*

data class ExchangeOrder(
    val exchange: EExchange,
    val id: String,
    val createdAt: Date,
    val updatedAt: Date,
    val status: EStatus,
    val expectedTrade: Trade,
    val executedTrade: Trade,
    val operations: List<ExchangeOrderOperation>
) {
    val expectedMakerFee: Asset
        get() = expectedTrade.from
            .run { copy(amount = amount * exchange.markets[expectedTrade.market]!!.makerFeePercent / 100.0) }

    val expectedTakerFee: Asset
        get() = expectedTrade.to
            .run { copy(amount = amount * exchange.markets[expectedTrade.market]!!.takerFeePercent / 100.0) }

    val isMaker: Boolean
        get() = true == cumulatedFees?.amount?.run { this > .0 }

    val isTaker: Boolean
        get() = true == cumulatedFees?.amount?.run { this < .0 }

    val cumulatedFees: Asset?
        get() = operations.filter { it.isFee }
            .map { it.asset }.groupBy { it.currency }
            .mapValues { it.value.reduce { a, b -> a.copy(amount = a.amount + b.amount) } }
            .values
            .singleOrNull()

    val executionPercent: Double
        get() = executedTrade.to.amount * 100 / expectedTrade.to.amount

    enum class EStatus {
        PENDING,
        ACTIVE,
        EXECUTED,
        CANCELED
    }
}
