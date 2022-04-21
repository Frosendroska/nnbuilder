package org.hse.nnbuilder.services

import net.devh.boot.grpc.server.service.GrpcService
import org.hse.nnbuilder.auth.JwtUtil
import org.hse.nnbuilder.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

@GrpcService
open class AuthService : AuthServiceGrpcKt.AuthServiceCoroutineImplBase() {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var jwtToken: JwtUtil

    @Override
    override suspend fun register(request: Auth.RegisterRequest): Auth.RegisterResponse {
        userService.save(request.name, request.email, request.password)
        return Auth.RegisterResponse.newBuilder().build()
    }

    @Override
    override suspend fun login(request: Auth.LoginRequest): Auth.LoginResponse {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.email, request.password) // principal, credentials
        )
        val jwt = jwtToken.generateJwtToken(authentication)
        return Auth.LoginResponse.newBuilder()
            .setToken(jwt)
            .build()
    }
}
