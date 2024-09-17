package org.sqvobs.argos.application.service

import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.sqvobs.argos.application.port.out.IncidenceExtractor
import org.sqvobs.argos.domain.*

class CollectIncidenceHandlerShould {

    private val incidenceExtractor: IncidenceExtractor = mock()

    private val collectIncidenceHandler = CollectIncidenceHandler(incidenceExtractor)

    @Test
    fun `should call extract and collect incidences until there were no more`() {
        val incidencesPage1 = listOf(oneIncidence(), oneIncidence())
        val noMoreIncidences = emptyList<Incidence>()

        whenever(incidenceExtractor.extract(CollectIncidenceHandler.Paging(1, 90)))
            .thenReturn(incidencesPage1)
        whenever(incidenceExtractor.extract(CollectIncidenceHandler.Paging(2, 90)))
            .thenReturn(noMoreIncidences)

        collectIncidenceHandler.collect()

        verify(incidenceExtractor).extract(CollectIncidenceHandler.Paging(1, 90))
        verify(incidenceExtractor).extract(CollectIncidenceHandler.Paging(2, 90))

        verifyNoMoreInteractions(incidenceExtractor)
    }

    private fun oneIncidence(): Incidence =
        Incidence(
            id = IncidenceId("5af1fb7a57f45b4a87c05853"),
            type = IncidenceType("Barrera urbanística/Obstáculos"),
            description = IncidenceDescription("Bordillo en mal estado"),
            requestedDate = RequestedDate(),
            address = Address("Barrio, Ronda Ntra. Sra. de la Oliva, 25, 41013 Sevilla, Spain"),
            coordinates = Coordinates(37.2956323845572, -5.8612060546875)
        )

}