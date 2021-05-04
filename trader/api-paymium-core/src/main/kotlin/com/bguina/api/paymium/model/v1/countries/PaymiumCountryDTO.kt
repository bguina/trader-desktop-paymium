package com.bguina.api.paymium.model.v1.countries

data class PaymiumCountryDTO(
    val accepted: Boolean? = null,
    val card_partner_accepted: Boolean? = null,
    val id: Long? = null,
    val iso_alpha2: String? = null,
    val iso_alpha3: String? = null,
    val iso_numeric: Long? = null,
    val name_de: String? = null,
    val name_en: String? = null,
    val name_es: String? = null,
    val name_fr: String? = null,
    val name_it: String? = null,
    val telephone_code: Int? = null
)
