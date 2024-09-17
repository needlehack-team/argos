package org.sqvobs.argos.application.port.out

import org.sqvobs.argos.domain.Incidence

interface Incidences {

    fun save(incidence: Incidence)

    fun saveAll(incidences: List<Incidence>)
}