package org.hse.nnbuilder

import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

/**
 * Sprint Boot Entry Point
 */
@SpringBootApplication(exclude = [SecurityAutoConfiguration::class, ReactiveSecurityAutoConfiguration::class])
open class NnbuilderApplication

fun main(args: Array<String>) {
    runApplication<NnbuilderApplication>(*args) {
        this.webApplicationType = WebApplicationType.REACTIVE
    }
}


