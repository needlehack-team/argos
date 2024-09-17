package org.sqvobs.argos.infrastructure.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface JpaIncidenceRepository : JpaRepository<JpaIncidence, Long>
