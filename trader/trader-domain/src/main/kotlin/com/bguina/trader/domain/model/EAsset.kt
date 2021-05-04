package com.bguina.trader.domain.model

import java.text.DecimalFormat
import kotlin.math.pow

enum class EAsset(
    val code: String,
    val floatingDecimals: Int
) {
    EURO("EUR", 2),
    USD("USD", 2),
    BITCOIN("BTC", 8),
    ;

    val epsilon: Double = 10.0.pow(-floatingDecimals)

    fun format(
        amount: Double
    ): String = DecimalFormat(
        "#." + "#".repeat(floatingDecimals)
    ).format(amount).plus(' ').plus(code)
}
