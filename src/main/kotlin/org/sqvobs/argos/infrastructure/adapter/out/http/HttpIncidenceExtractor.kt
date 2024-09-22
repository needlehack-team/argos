package org.sqvobs.argos.infrastructure.adapter.out.http

import com.fasterxml.jackson.annotation.JsonProperty
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import org.sqvobs.argos.application.port.out.IncidenceExtractor
import org.sqvobs.argos.application.service.CollectIncidenceHandler
import org.sqvobs.argos.domain.*

class HttpIncidenceExtractor(private val restTemplate: RestTemplate) : IncidenceExtractor {

    companion object {
        private val log = LoggerFactory.getLogger(HttpIncidenceExtractor::class.java)
        private const val INDICENCE_URL = "https://api-pgics.sevilla.org/requests"
    }

    override fun extract(page: CollectIncidenceHandler.Paging): List<Incidence> {
        val response = restTemplate.exchange(
            UriComponentsBuilder
                .fromHttpUrl(INDICENCE_URL)
                .queryParam("jurisdiction_ids", "org.sevilla")
                .queryParam("limit", page.limit)
                .queryParam("page", page.offset)
                .build()
                .toUri(),
            HttpMethod.GET,
            null,
            object : ParameterizedTypeReference<List<ExternalIncidence>>() {}
        ).body
        return response?.map(::fromExternalIncidence) ?: emptyList()
    }


    data class ExternalIncidence(
        @JsonProperty("service_id")
        val serviceId: String,
        @JsonProperty("service_name")
        val serviceName: String,
        @JsonProperty("requested_datetime")
        val requestedDatetime: String,
        val address: String,
        val description: String?,
        val lat: Double,
        val long: Double
    )

    fun fromExternalIncidence(externalIncidence: ExternalIncidence): Incidence {
        return Incidence(
            id = IncidenceId(externalIncidence.serviceId),
            type = IncidenceType(externalIncidence.serviceName),
            description = externalIncidence.description?.let { IncidenceDescription(it) },
            requestedDate = RequestedDate(externalIncidence.requestedDatetime),
            address = Address(externalIncidence.address),
            coordinates = Coordinates(externalIncidence.lat, externalIncidence.long)
        )
    }
}
