package org.sqvobs.argos.domain

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class RequestedDate(val value: LocalDateTime = LocalDateTime.now()) {

    constructor(value: String): this(
        LocalDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME)
    )
}
