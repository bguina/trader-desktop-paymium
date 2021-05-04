package com.bguina.api.paymium

import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

object LocalProperties {
    const val LOCAL_PROPERTIES_PROP_PAYMIUM_EMAIL = "paymium.email"
    const val LOCAL_PROPERTIES_PROP_PAYMIUM_API_KEY = "paymium.api_key"
    const val LOCAL_PROPERTIES_PROP_PAYMIUM_SECRET = "paymium.secret"

    fun getProperty(
        key: String
    ): String = properties.getProperty(key)
        ?: throw NoSuchFieldException("no $key key is defined in LocalProperties")

    private val ROOT_PROJECT_PATH = Paths.get("../..")
        .toAbsolutePath().normalize()
        .toString()

    private val LOCAL_PROPERTIES_FILE: String = ROOT_PROJECT_PATH
        .plus("/local.properties")

    private val properties: Properties = FileInputStream(LOCAL_PROPERTIES_FILE)
        .use { input -> Properties().apply { load(input) } }
}
