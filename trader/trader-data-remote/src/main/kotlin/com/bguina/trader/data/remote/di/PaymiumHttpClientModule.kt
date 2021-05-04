package com.bguina.trader.data.remote.di

import com.bguina.api.paymium.rx2.Rx2PaymiumApiClient
import com.bguina.trader.util.extensions.readPropertyFile
import com.bguina.trader.util.interceptor.Log4jOkHttpLoggingInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import java.util.*

@Module
object PaymiumHttpClientModule {
    @Provides
    fun providePaymiumHttpClient(
        baseHttpClient: OkHttpClient
    ): Rx2PaymiumApiClient = Rx2PaymiumApiClient(
        apiKey = LOCAL_PROPERTIES_FILE.getProperty(LOCAL_PROPERTIES_PROP_PAYMIUM_API_KEY)
            ?: throw NoSuchFieldException("no api key defined for Paymium"),
        secret = LOCAL_PROPERTIES_FILE.getProperty(LOCAL_PROPERTIES_PROP_PAYMIUM_SECRET)
            ?: throw NoSuchFieldException("no api key defined for Paymium"),
        baseHttpClient = baseHttpClient.newBuilder()
            .addInterceptor(
                Log4jOkHttpLoggingInterceptor(
                    logger = LogManager.getLogger(Rx2PaymiumApiClient::class.java),
                    logLevel = Level.INFO
                )
            )
            .build()
    )

    private val LOCAL_PROPERTIES_FILE: Properties = "local.properties".readPropertyFile()
    private const val LOCAL_PROPERTIES_PROP_PAYMIUM_API_KEY = "paymium.api_key"
    private const val LOCAL_PROPERTIES_PROP_PAYMIUM_SECRET = "paymium.secret"
}
