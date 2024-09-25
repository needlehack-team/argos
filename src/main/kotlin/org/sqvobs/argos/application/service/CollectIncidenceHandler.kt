package org.sqvobs.argos.application.service

import org.slf4j.LoggerFactory
import org.sqvobs.argos.application.port.`in`.CollectIncidence
import org.sqvobs.argos.application.port.out.IncidenceExtractor
import org.sqvobs.argos.application.port.out.Incidences
import java.util.*
import kotlin.time.Duration.Companion.seconds

class CollectIncidenceHandler(
    private val incidenceExtractor: IncidenceExtractor,
    private val repository: Incidences
) : CollectIncidence {

    companion object {
        private val log = LoggerFactory.getLogger(CollectIncidenceHandler::class.java)
        private const val TWO_SECONDS: Int = 2
        private const val LIMIT_PAGE_TO_COLLECT: Int = 111
    }

    override fun collect() {
        generateSequence(Paging(1, 90)) { previousPaging -> previousPaging.next() }
            .takeWhile { paging -> paging.offset <= LIMIT_PAGE_TO_COLLECT }
            .map { paging ->
                stopSeconds(TWO_SECONDS)
                incidenceExtractor.extract(paging)
            }
            .onEach { incidences -> repository.saveAll(incidences) }
            .onEach { incidences -> log.info("Incidences collected $incidences") }
            .toList()

    }

    private fun stopSeconds(seconds: Int) {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                log.debug("Throttling with $seconds seconds interval")
            }
        }, seconds.seconds.inWholeMilliseconds)
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