package com.bguina.api.paymium.kt

import com.bguina.api.paymium.PaymiumTests
import com.bguina.api.paymium.LocalProperties
import org.apache.logging.log4j.LogManager

class KtPaymiumTests : PaymiumTests(
    KtPaymium(
        apiKey = LocalProperties.getProperty(LocalProperties.LOCAL_PROPERTIES_PROP_PAYMIUM_API_KEY),
        secret = LocalProperties.getProperty(LocalProperties.LOCAL_PROPERTIES_PROP_PAYMIUM_SECRET),
    ),
    LogManager.getLogger(KtPaymiumTests::class.java)
)
