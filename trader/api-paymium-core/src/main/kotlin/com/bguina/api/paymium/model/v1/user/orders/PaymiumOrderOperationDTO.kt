package com.bguina.api.paymium.model.v1.user.orders

import java.util.*

data class PaymiumOrderOperationDTO(
    val address: String? = null,
    val amount: String? = null,
    val created_at: Date? = null,
    val created_at_int: Int? = null,
    val currency: String? = null,
    val is_trading_account: Boolean? = null,
    val name: String? = null,
    val tx_hash: String? = null,
    val uuid: String? = null
) {
    companion object {
        const val NAME_BTC_PURCHASE : String = "btc_purchase"
        const val NAME_BTC_PURCHASE_FEE : String = "btc_purchase_fee"
        const val NAME_BTC_SALE : String = "btc_sale"
        const val NAME_BTC_SALE_FEE : String = "btc_sale_fee"
        const val NAME_BTC_SALE_FEE_INCENTIVE : String = "btc_sale_fee_incentive"
        const val NAME_BTC_PURCHASE_FEE_INCENTIVE : String = "btc_purchase_fee_incentive"
        const val NAME_LOCK : String = "lock"
        const val NAME_UNLOCK : String = "unlock"
    }
}
