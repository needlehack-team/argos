package org.sqvobs.argos.application.service

import org.slf4j.LoggerFactory
import org.sqvobs.argos.application.port.`in`.CollectIncidence
import org.sqvobs.argos.application.port.out.IncidenceExtractor

class CollectIncidenceHandler(private val incidenceExtractor: IncidenceExtractor) : CollectIncidence {

    companion object {
        private val log = LoggerFactory.getLogger(CollectIncidenceHandler::class.java)
    }

    override fun collect() {
        log.info("Incidences {}", incidenceExtractor.extract())
    }
}