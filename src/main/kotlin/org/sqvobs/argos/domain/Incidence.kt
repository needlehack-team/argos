package org.sqvobs.argos.domain

data class Incidence(
    val id: IncidenceId,
    val type: IncidenceType,
    val description: IncidenceDescription?,
    val requestedDate: RequestedDate,
    val address: Address?,
    val coordinates: Coordinates,
)
