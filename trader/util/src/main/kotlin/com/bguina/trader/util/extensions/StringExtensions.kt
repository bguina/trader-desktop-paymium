package com.bguina.trader.util.extensions

import java.io.FileInputStream
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

fun String.readPropertyFile(
): Properties = FileInputStream(this)
    .use { input -> Properties().apply { load(input) } }
