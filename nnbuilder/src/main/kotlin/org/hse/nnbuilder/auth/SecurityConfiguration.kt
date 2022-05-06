package org.hse.nnbuilder.auth

import net.devh.boot.grpc.server.security.authentication.BearerAuthenticationReader
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken

@Configuration(proxyBeanMethods = false)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, proxyTargetClass = true)
open class SecurityConfiguration {

    @Autowired
    private lateinit var userDetailsService: UserDetailsServiceImpl

    @Autowired
    private lateinit var jwtToken: JwtUtil

    @Bean
    open fun authenticationManager(): AuthenticationManager {
        val daoAuthenticationProvider = DaoAuthenticationProvider()
        daoAuthenticationProvider.setUserDetailsService(userDetailsService)
        daoAuthenticationProvider.setPasswordEncoder(BCryptPasswordEncoder(10))

        val preAuthenticatedAuthenticationProvider = PreAuthenticatedAuthenticationProvider()
        val userDetailsWrapper =
            UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken>(userDetailsService)
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(userDetailsWrapper)

        return ProviderManager(daoAuthenticationProvider, preAuthenticatedAuthenticationProvider)
    }

    // Reads the authentication data from the ServerCall
    @Bean
    open fun authenticationReader(): GrpcAuthenticationReader {
        return BearerAuthenticationReader { token -> jwtToken.parseJwtToken(token) }
    }

    @Bean
    open fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder(10)
    }
}
