package org.sqvobs.argos.infrastructure.adapter.out.http

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.sqvobs.argos.domain.*
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime

@Component
class HttpTinyBirdEventCreator() {

    companion object {
        private val log = LoggerFactory.getLogger(HttpTinyBirdEventCreator::class.java)
        private const val INDICENCE_URL = "https://api-pgics.sevilla.org/requests"
    }

    fun generateEvents(incidences: List<Incidence>) {

        val url = "https://api.eu-central-1.aws.tinybird.co/v0/events?name=incidences2"
        val auth = "TBD"

        if (auth == "TBD"){
            throw RuntimeException("Define bearer token from tinybird")
        }

        incidences
            .map {
                IncidenceEvent(Instant.now().toString(), it.id.value, it.type.value, it.description?.value ?:"null", it.requestedDate.value.toString(), it.address?.value ?:"null", it.coordinates.latitude, it.coordinates.longitude, )
            }.map {
                Json.encodeToString(it)
            }
            .onEach {
                postEvent(url, auth, it)
            }


    }

    private fun postEvent(url: String, auth: String, data: String) {
        val con = URL(url).openConnection() as HttpURLConnection
        con.requestMethod = "POST"
        con.setRequestProperty("Authorization", auth)
        con.setRequestProperty("Content-Type", "application/json")
        con.doOutput = true
        con.outputStream.write(data.toByteArray(StandardCharsets.UTF_8))

        println(String(con.inputStream.readAllBytes(), StandardCharsets.UTF_8))
    }

    @Serializable
    data class IncidenceEvent(
            val timestamp: String,
            val id: String,
            val type: String,
            val description: String?,
            val requestedDate: String,
            val address: String?,
            val latitude: Double,
            val longitude: Double
    )
}
