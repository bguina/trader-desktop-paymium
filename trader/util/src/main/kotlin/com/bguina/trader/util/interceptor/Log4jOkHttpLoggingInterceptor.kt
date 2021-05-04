package com.bguina.trader.util.interceptor

/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import okhttp3.*
import okio.Buffer
import okio.BufferedSource
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.Logger
import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException
import java.util.concurrent.TimeUnit

/**
 * An OkHttp interceptor which logs request and response information. Can be applied as an
 * [OkHttpClient.interceptors] or as a [OkHttpClient.networkInterceptors].
 *
 * The format of the logs created by
 * this class should not be considered stable and may change slightly between releases. If you need
 * a stable logging format, use your own interceptor.
 */
class Log4jOkHttpLoggingInterceptor(
    private val logger: Logger,
    private val logLevel: Level,
    private val prettyBody: Boolean = true,
    private val hideLargeBody: Boolean = false,
    private val hideEncodedBody: Boolean = true
) : Interceptor {

    /** Change the level at which this interceptor logs.  */
    @Volatile
    var httpLevel = HttpLevel.BODY

    @Suppress("UNUSED")
    enum class HttpLevel {
        /** No logs.  */
        NONE,

        /**
         * Logs request and response lines.
         *
         *
         * Example:
         * <pre>`--> POST /greeting http/1.1 (3-byte body)
         *
         * <-- 200 OK (22ms, 6-byte body)
        `</pre> *
         */
        BASIC,

        /**
         * Logs request and response lines and their respective headers.
         *
         *
         * Example:
         * <pre>`--> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
        `</pre> *
         */
        HEADERS,

        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         *
         *
         * Example:
         * <pre>`--> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         *
         * Hi?
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         *
         * Hello!
         * <-- END HTTP
        `</pre> *
         */
        BODY
    }

    private fun log(message: String) {
        val loggerMethod: ((String) -> Unit)? = when (logLevel) {
            Level.FATAL -> logger::fatal
            Level.ERROR -> logger::error
            Level.WARN -> logger::warn
            Level.INFO -> logger::info
            Level.DEBUG -> logger::debug
            Level.TRACE, Level.ALL -> logger::trace
            else -> null
        }
        loggerMethod?.invoke(message) ?: throw IllegalStateException()
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val level = this.httpLevel
        val request: Request = chain.request()

        if (level == HttpLevel.NONE) return chain.proceed(request)

        val logBody = level == HttpLevel.BODY
        val logHeaders = logBody || level == HttpLevel.HEADERS

        val requestBody = request.body()
        val hasRequestBody = requestBody != null

        val connection = chain.connection()
        val protocol = if (connection != null) connection.protocol() else Protocol.HTTP_1_1

        var requestStartMessage = "--> ${request.method()} ${request.url()} $protocol"
        if (!logHeaders && hasRequestBody) {
            requestStartMessage += " (${requestBody!!.contentLength()}-byte body)"
        }
        log(requestStartMessage)

        if (logHeaders) {
            log("Headers: ${request.headers().size()}")
            if (hasRequestBody) {
                // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
                if (requestBody!!.contentType() != null) {
                    log("Content-Type: " + requestBody.contentType()!!)
                }
                if (requestBody.contentLength() != -1L) {
                    log("Content-Length: " + requestBody.contentLength())
                }
            }

            val headers = request.headers()
            for (i: Int in (0 until headers.size())) {
                val name = headers.name(i)
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equals(name, ignoreCase = true) && !"Content-Length".equals(
                        name,
                        ignoreCase = true
                    )
                ) {
                    // Skip sensitive outbound headers
                    if (SENSITIVE_HEADERS.none { name.contains(it, ignoreCase = true) }) {
                        log(name + ": " + headers.value(i))
                    }
                }
            }

            if (!logBody || !hasRequestBody) {
                log("--> END " + request.method())
            } else if (hideLargeBody && bodyTooLarge(request.headers())) {
                log("<-- END " + request.method() + " (large body omitted)")
            } else if (hideEncodedBody && bodyEncoded(request.headers())) {
                log("--> END " + request.method() + " (encoded body omitted)")
            } else {
                val buffer = Buffer()
                requestBody!!.writeTo(buffer)

                var charset: Charset? = UTF8
                val contentType = requestBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }

                log("")
                if (isPlaintext(buffer)) {
                    log(formatBody(contentType?.subtype(), buffer.readString(charset!!)))

                    log(
                        "--> END " + request.method()
                                + " (" + requestBody.contentLength() + "-byte body)"
                    )
                } else {
                    log(
                        "--> END " + request.method() + " (binary "
                                + requestBody.contentLength() + "-byte body omitted)"
                    )
                }
            }
        }

        val startNs = System.nanoTime()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            log("<-- HTTP FAILED: $e: ${e.cause} ${request.url()}")
            throw e
        }

        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        val responseBody = response.body()
        val contentLength = responseBody!!.contentLength()
        val bodySize = if (contentLength != -1L) "$contentLength-byte" else "unknown-length"
        log(
            "<-- ${response.code()} ${response.message()} ${
                response.request().url()
            } (${tookMs}ms" + (if (!logHeaders) ", $bodySize body" else "") + ')'.toString()
        )

        if (logHeaders) {
            val headers: Headers = response.headers()

            for (i: Int in (0 until headers.size())) {
                val name = headers.name(i)
                // Skip sensitive inbound headers
                if (SENSITIVE_HEADERS.none { name.contains(it, ignoreCase = true) }) {
                    log(name + ": " + headers.value(i))
                }
            }

            if (!logBody) {
                log("<-- END HTTP")
            } else if (hideLargeBody && bodyTooLarge(response.headers())) {
                log("<-- END HTTP (large body omitted)")
            } else if (hideEncodedBody && bodyEncoded(response.headers())) {
                log("<-- END HTTP (encoded body omitted)")
            } else {
                val source: BufferedSource = responseBody.source()

                @Suppress("UsePropertyAccessSyntax")
                val buffer: Buffer = source.buffer
                source.request(Long.MAX_VALUE) // buffer the entire body

                var charset: Charset? = UTF8
                val contentType: MediaType? = responseBody.contentType()
                if (contentType != null) {
                    try {
                        charset = contentType.charset(UTF8)
                    } catch (e: UnsupportedCharsetException) {
                        log("")
                        log("Couldn't decode the response body; charset is likely malformed.")
                        log("<-- END HTTP")

                        return response
                    }
                }

                if (hideEncodedBody && !isPlaintext(buffer)) {
                    log("")
                    log("<-- END HTTP (binary " + buffer.size() + "-byte body omitted)")
                    return response
                }

                if (contentLength != 0L) {
                    log("")
                    log(formatBody(contentType?.subtype(), buffer.clone().readString(charset!!)))
                }

                log("<-- END HTTP (" + buffer.size() + "-byte body)")
            }
        }

        return response
    }

    private fun bodyTooLarge(
        headers: Headers
    ): Boolean = true == headers.get("Content-Length")?.toLong()?.run { this > 1024 }

    private fun bodyEncoded(
        headers: Headers
    ): Boolean = false == headers.get("Content-Encoding")?.equals("identity", ignoreCase = true)

    private fun formatBody(subtype: String?, body: String): String = when {
        prettyBody -> when (subtype) {
            "json" -> GsonBuilder()
                .setLenient()
                .setPrettyPrinting()
                .create()
                .run { toJson(fromJson(body, JsonElement::class.java)) }
            else -> null
        }
        else -> null
    } ?: body

    companion object {
        private val UTF8 = Charset.forName("UTF-8")
        private val SENSITIVE_HEADERS: List<String> = listOf(
            //"authorization",
            "api-key",

            // fixme: do not print out "boring" headers?
            "server",
            "date",
            "connection",
            "expires",
            "vary",
            "allow",
            "dynaTrace",
            "via",
            "x-correlation-id",
            "x-dynatrace",
            "dynaTrace"
        )
        .takeIf { false } ?: listOf()

        /**
         * Returns true if the body in question probably contains human readable text. Uses a small sample
         * of code points to detect unicode control characters commonly used in binary file signatures.
         */
        internal fun isPlaintext(
            buffer: Buffer
        ): Boolean {
            return try {
                val prefix = Buffer()
                val byteCount = if (buffer.size() < 64) buffer.size() else 64
                buffer.copyTo(prefix, 0, byteCount)
                for (i in 0..15) {
                    if (prefix.exhausted()) {
                        break
                    }
                    val codePoint = prefix.readUtf8CodePoint()
                    if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                        return false
                    }
                }
                true
            } catch (e: EOFException) {
                false // Truncated UTF-8 sequence.
            }
        }
    }
}
