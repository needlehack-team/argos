package org.sqvobs.argos.application.port.out

import org.sqvobs.argos.application.service.CollectIncidenceHandler
import org.sqvobs.argos.domain.Incidence

fun interface IncidenceExtractor {

    fun extract(page: CollectIncidenceHandler.Paging): List<Incidence>
}