package com.bguina.trader.util.extensions

import java.text.DecimalFormat

fun Double.format(
    floatingDecimals: Int? = null
): String {
    return when {
        null != floatingDecimals ->
            DecimalFormat("#." + "#".repeat(floatingDecimals)).format(this)
        else -> toString()
    }
}
