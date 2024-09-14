package org.sqvobs.argos.infrastructure.adapter.`in`.shell

import org.sqvobs.argos.application.port.`in`.CollectIncidence

//@ShellComponent
class LoadIncidenceCommand(val port: CollectIncidence) {

//    @ShellMethod("Collect incidences")
    fun loadIncidences(): String {
        port.collect()
        return "All incidences have been collected"
    }
}