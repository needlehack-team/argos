package org.sqvobs.argos

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.sqvobs.argos.application.port.`in`.CollectIncidence


@SpringBootApplication
@EnableJpaRepositories
class ArgosApplication

fun main(args: Array<String>) {

    val context: ApplicationContext = runApplication<ArgosApplication>(*args)

    if (args.any { it == "command=collect-incidences" }) {
        val collectIncidence = context.getBean(CollectIncidence::class.java)
        collectIncidence.collect()
    }
}