package org.sqvobs.argos.infrastructure.adapter.out.http

import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import org.sqvobs.argos.application.port.out.IncidenceExtractor

class HttpIncidenceExtractor(private val restTemplate: RestTemplate) : IncidenceExtractor {

    companion object {
        private val log = LoggerFactory.getLogger(HttpIncidenceExtractor::class.java)
        private const val INDICENCE_URL = "https://api-pgics.sevilla.org"
    }

    override fun extract(): String =
        restTemplate.exchange(
            UriComponentsBuilder
                .fromHttpUrl(INDICENCE_URL)
                .path("/requests")
                .uriVariables(mapOf("jurisdiction_ids" to "org.sevilla", "limit" to 90, "page" to 1))
                .build()
                .toUri(),
            HttpMethod.GET,
            null,
            object : ParameterizedTypeReference<String>() {}
        ).body ?: ""
}