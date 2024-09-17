package org.sqvobs.argos.infrastructure.adapter.out.persistence

import org.sqvobs.argos.application.port.out.Incidences
import org.sqvobs.argos.domain.Incidence

class JpaIncidences(private val repository: JpaIncidenceRepository) : Incidences {

    override fun save(incidence: Incidence) {
        repository.save(JpaIncidence.fromDomain(incidence))
    }

    override fun saveAll(incidences: List<Incidence>) {
        repository.saveAll(incidences.map { JpaIncidence.fromDomain(it) })
    }
}