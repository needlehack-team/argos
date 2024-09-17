package org.sqvobs.argos.application.service

import org.slf4j.LoggerFactory
import org.sqvobs.argos.application.port.`in`.CollectIncidence
import org.sqvobs.argos.application.port.out.IncidenceExtractor
import java.util.*

class CollectIncidenceHandler(private val incidenceExtractor: IncidenceExtractor) : CollectIncidence {

    companion object {
        private val log = LoggerFactory.getLogger(CollectIncidenceHandler::class.java)
    }

    override fun collect() {

        generateSequence(Paging(107, 90)) { previousPaging -> previousPaging.next() }
            .map { paging ->
                Timer().schedule(object : TimerTask() {
                    override fun run() {
                        // TODO Extract this functionality to implementation details
                    }
                }, 2000)
                incidenceExtractor.extract(paging)
            }
            .onEach { incidences -> log.info("Incidences collected $incidences") }
            .takeWhile { incidences -> incidences.isNotEmpty() }
            .toList()
    }

    data class Paging(val offset: Int = 1, val limit: Int = 90) {

        init {
            require(offset >= 0) { "Paging offset must be non-negative" }
            require(limit > 0) { "Paging limit must be greater than zero" }
        }

        fun next(): Paging {
            return Paging(this.offset.inc(), this.limit)
        }
    }
}