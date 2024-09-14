package org.sqvobs.argos.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import org.sqvobs.argos.application.port.out.IncidenceExtractor
import org.sqvobs.argos.application.service.CollectIncidenceHandler
import org.sqvobs.argos.infrastructure.adapter.out.http.HttpIncidenceExtractor

@Configuration
class BeanInitializr {

    @Bean
    fun restTemplate(): RestTemplate = RestTemplate()

    @Bean
    fun incidenceExtractor(restTemplate: RestTemplate): IncidenceExtractor = HttpIncidenceExtractor(restTemplate)

    @Bean
    fun collectIncidence(incidenceExtractor: IncidenceExtractor): CollectIncidenceHandler = CollectIncidenceHandler(incidenceExtractor)
}