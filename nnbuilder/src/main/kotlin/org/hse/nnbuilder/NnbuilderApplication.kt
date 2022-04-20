package org.hse.nnbuilder

import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Sprint Boot Entry Point
 */
@SpringBootApplication
open class NnbuilderApplication

fun main(args: Array<String>) {
    runApplication<NnbuilderApplication>(*args) {
        this.webApplicationType = WebApplicationType.REACTIVE
    }
}
