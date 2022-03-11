package org.hse.nnbuilder

import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Controller


@SpringBootApplication
open class NnbuilderApplication

@Controller
open class StaticApp

fun main(args: Array<String>) {
    runApplication<NnbuilderApplication>(*args) {
        this.webApplicationType = WebApplicationType.REACTIVE
    }
}
