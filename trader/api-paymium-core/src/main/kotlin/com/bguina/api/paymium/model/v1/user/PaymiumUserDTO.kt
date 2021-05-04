package com.bguina.api.paymium.model.v1.user

data class PaymiumUserDTO(
    val balance_btc: String? = null,
    val balance_eur: String? = null,
    val channel_id: String? = null,
    val email: String? = null,
    val locale: String? = null,
    val locked_btc: String? = null,
    val locked_eur: String? = null,
    val meta_state: String? = null,
    val name: String? = null
) {
    val isApproved: Boolean
        get() = META_STATE_APPROVED == meta_state

    companion object {
        const val META_STATE_APPROVED : String = "approved"
    }
}
