package com.bguina.api.paymium.auth

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class PaymiumAuthTokenInterceptor(
    private val apiKey: String,
    private val secret: String
) : Interceptor {

    private var nonce: Long = 0

    override fun intercept(
        chain: Interceptor.Chain
    ): Response = chain.request().run {
        val queryNonce = nextNonce()
        val querySignature = signature(queryNonce)

        newBuilder()
            .header(AUTH_HEADER_API_KEY, apiKey)
            .header(AUTH_HEADER_SIGNATURE, querySignature)
            .header(AUTH_HEADER_NONCE, queryNonce.toString())
    }
        .build()
        .run(chain::proceed)

    private fun nextNonce() : Long {
        ++nonce

        System.currentTimeMillis().run {
            if (nonce < this)
                nonce = this
        }

        return nonce
    }

    private fun Request.signature(
        nonce: Long
    ): String {
        val message = "${url()}${body()?.toString().orEmpty()}"
        val auth = "${nonce}${message}"

        return Mac.getInstance("HmacSHA256")
            .apply { init(SecretKeySpec(secret.toByteArray(), algorithm)) }
            .doFinal(auth.toByteArray())
            .joinToString(separator = "") { byte -> "%02x".format(byte) }
    }

    companion object {
        private const val AUTH_HEADER_API_KEY: String = "Api-Key"
        private const val AUTH_HEADER_SIGNATURE: String = "Api-Signature"
        private const val AUTH_HEADER_NONCE: String = "Api-Nonce"
    }
}
