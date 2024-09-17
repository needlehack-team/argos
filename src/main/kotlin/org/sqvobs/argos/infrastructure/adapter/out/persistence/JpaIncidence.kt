package org.sqvobs.argos.infrastructure.adapter.out.persistence

import jakarta.persistence.*
import org.sqvobs.argos.domain.*
import java.time.LocalDateTime

@Entity
@Table(name = "incidence")
class JpaIncidence(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = true)
    val externalId: String? = null,

    @Column(nullable = true)
    val type: String? = null,

    @Column(nullable = true)
    @Lob
    @Basic(fetch = FetchType.LAZY)
    val description: String? = null,

    @Column
    val requestedDate: LocalDateTime,

    @Column(nullable = true)
    val address: String? = null,

    @Column(nullable = true)
    val latitude: Double,

    @Column(nullable = true)
    val longitude: Double
) {
    companion object {
        fun fromDomain(incidenceAggregate: Incidence): JpaIncidence {
            return JpaIncidence(
                externalId = incidenceAggregate.id.value,
                type = incidenceAggregate.type.value,
                description = incidenceAggregate.description?.value,
                requestedDate = incidenceAggregate.requestedDate.value,
                address = incidenceAggregate.address?.value,
                latitude = incidenceAggregate.coordinates.latitude,
                longitude = incidenceAggregate.coordinates.longitude
            )
        }
    }
}

fun JpaIncidence.toDomain() =
    Incidence(
        id = IncidenceId(this.externalId.orEmpty()),
        type = IncidenceType(this.type.orEmpty()),
        description = IncidenceDescription(this.description.orEmpty()),
        requestedDate = RequestedDate(this.requestedDate),
        address = Address(this.address.orEmpty()),
        coordinates = Coordinates(this.latitude, this.longitude)
    )