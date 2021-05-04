package com.bguina.api.paymium.model.v1.user.orders

enum class EOrderType(
    val identifier: String
) {
    LimitOrder("LimitOrder"),
    MarketOrder("MarketOrder"),
    BitcoinDeposit("BitcoinDeposit"),
    WireDeposit("WireDeposit"),
    Payment("Payment"),
    EmailTransfer("EmailTransfer"),
    EmailDeposit("EmailDeposit"),
    ;
    companion object {
        fun toString(values: Set<EOrderType>) = values
            .joinToString(",")
            { it.identifier }
    }
}
