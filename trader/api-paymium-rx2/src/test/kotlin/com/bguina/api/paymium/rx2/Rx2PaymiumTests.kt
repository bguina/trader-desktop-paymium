package com.bguina.api.paymium.rx2

import com.bguina.api.paymium.PaymiumTests
import com.bguina.api.paymium.LocalProperties
import org.apache.logging.log4j.LogManager

class Rx2PaymiumTests : PaymiumTests(
    Rx2Paymium(
        apiKey = LocalProperties.getProperty(LocalProperties.LOCAL_PROPERTIES_PROP_PAYMIUM_API_KEY),
        secret = LocalProperties.getProperty(LocalProperties.LOCAL_PROPERTIES_PROP_PAYMIUM_SECRET),
    ),
    LogManager.getLogger(Rx2PaymiumTests::class.java)
)
