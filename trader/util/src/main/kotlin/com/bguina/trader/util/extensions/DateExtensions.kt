package com.bguina.trader.util.extensions

import java.text.DateFormat
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

val Date.dateShortFormat: String
    get() = DateFormat.getDateInstance(DateFormat.SHORT).format(this)

val Date.dateMediumFormat: String
    get() = DateFormat.getDateInstance(DateFormat.MEDIUM).format(this)

val Date.isoLocalDateTimeFormat: String
    get() = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(toInstant().atZone(ZoneId.systemDefault()))
