package org.sqvobs.argos

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ArgosApplication

fun main(args: Array<String>) {
	runApplication<ArgosApplication>(*args)
}
