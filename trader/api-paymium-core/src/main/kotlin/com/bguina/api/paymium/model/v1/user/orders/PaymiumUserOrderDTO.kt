package com.bguina.api.paymium.model.v1.user.orders

import java.util.*

data class PaymiumUserOrderDTO(
    val account_operations: List<PaymiumOrderOperationDTO>? = null,
    val amount: String? = null,
    val btc_fee: String? = null,
    val comment: String? = null,
    val created_at: Date? = null,
    val currency: String? = null,
    val currency_amount: String? = null,
    val currency_fee: String? = null,
    val direction: String? = null,
    val price: String? = null,
    val state: String? = null,
    val traded_btc: String? = null,
    val traded_currency: String? = null,
    val type: String? = null,
    val updated_at: Date? = null,
    val uuid: String? = null
) {
    companion object {
        const val ORDER_DIRECTION_SELL: String = "sell"
        const val ORDER_DIRECTION_BUY: String = "buy"

        const val ORDER_TYPE_LIMIT_ORDER: String = "LimitOrder"
        const val ORDER_TYPE_MARKET_ORDER: String = "MarketOrder"
        const val ORDER_TYPE_BITCOIN_DEPOSIT: String = "BitcoinDeposit"
        const val ORDER_TYPE_WIRE_DEPOSIT: String = "WireDeposit"
        const val ORDER_TYPE_PAYMENT: String = "Payment"
        const val ORDER_TYPE_EMAIL_TRANSFER: String = "EmailTransfer"
        const val ORDER_TYPE_EMAIL_DEPOSIT: String = "EmailDeposit"

        const val ORDER_STATE_PENDING_EXECUTION: String = "pending_execution"
        const val ORDER_STATE_ACTIVE: String = "active"
        const val ORDER_STATE_EXECUTED: String = "executed"
        const val ORDER_STATE_FILLED: String = "filled"
        const val ORDER_STATE_CANCELED: String = "canceled"
    }
}
