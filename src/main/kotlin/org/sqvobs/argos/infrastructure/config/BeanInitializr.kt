package org.sqvobs.argos.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import org.sqvobs.argos.application.port.out.IncidenceExtractor
import org.sqvobs.argos.application.port.out.Incidences
import org.sqvobs.argos.application.service.CollectIncidenceHandler
import org.sqvobs.argos.infrastructure.adapter.out.http.HttpIncidenceExtractor
import org.sqvobs.argos.infrastructure.adapter.out.http.HttpTinyBirdEventCreator
import org.sqvobs.argos.infrastructure.adapter.out.persistence.JpaIncidenceRepository
import org.sqvobs.argos.infrastructure.adapter.out.persistence.JpaIncidences

@Configuration
class BeanInitializr {

    @Bean
    fun incidences(repository: JpaIncidenceRepository): Incidences = JpaIncidences(repository)

    @Bean
    fun restTemplate(): RestTemplate = RestTemplate()

    @Bean
    fun incidenceExtractor(restTemplate: RestTemplate): IncidenceExtractor = HttpIncidenceExtractor(restTemplate)

    @Bean
    fun collectIncidence(incidenceExtractor: IncidenceExtractor, repository: Incidences, tinyBirdEventCreator: HttpTinyBirdEventCreator): CollectIncidenceHandler =
        CollectIncidenceHandler(incidenceExtractor, repository, tinyBirdEventCreator)
}