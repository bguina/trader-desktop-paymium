package com.bguina.trader.domain.model

import kotlin.math.abs

data class Asset(
    val amount: Double,
    val currency: EAsset
) {
    override fun toString(): String = currency.format(amount)

    override fun hashCode(): Int = 31 * amount.hashCode() + currency.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        when (other) {
            is Asset -> {
                if (currency != other.currency) return false
                return equals(other.amount)
            }
            is Double -> return abs(amount - other) < currency.epsilon
        }
        return false
    }

    val depleted: Boolean
        get() = this.equals(.0)

    fun format(
        sign: Boolean = false
    ): String = "${amount.sign} ".takeIf { sign }.orEmpty()
        .plus(currency.format(abs(amount)))

    private val Double.sign: Char
        get() = if (this >= 0) '+' else '-'
}
