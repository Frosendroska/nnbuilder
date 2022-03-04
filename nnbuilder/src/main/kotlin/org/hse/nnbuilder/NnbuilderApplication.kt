package org.hse.nnbuilder

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class NnbuilderApplication

fun main(args: Array<String>) {
	runApplication<NnbuilderApplication>(*args)
}
