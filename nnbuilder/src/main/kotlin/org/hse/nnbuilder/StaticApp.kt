package org.hse.nnbuilder

import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.RouterFunctions.resources
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

/**
 * Empty app used to enable a web server to provide static files.
 */
@Controller
open class StaticApp {

    // Disable auth for static files (it is handled at the api level)
    @Bean
    open fun security(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http.formLogin().disable().build()
    }

    // Serve static files with fallback for a single-page application
    // https://github.com/spring-projects/spring-framework/issues/27257
    @Bean
    fun client(): RouterFunction<ServerResponse> = resources { request ->
        resourceLookup.apply(request)
    }

    private val resourceLookup = RouterFunctions
        .resourceLookupFunction("/**", ClassPathResource("static/"))
        .andThen {
            it.switchIfEmpty(Mono.just(ClassPathResource("static/index.html")))
        }
}
