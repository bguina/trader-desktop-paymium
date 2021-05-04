package com.bguina.api.paymium.model.v1.user.addresses

data class PaymiumCoinAddressDTO(
    val address: String? = null,
    val currency: String? = null,
    val label: String? = null,
    val valid_until: Int? = null
)
