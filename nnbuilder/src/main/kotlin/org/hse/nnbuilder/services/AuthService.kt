package org.hse.nnbuilder.services

import net.devh.boot.grpc.server.service.GrpcService
import org.hse.nnbuilder.auth.JwtUtil
import org.hse.nnbuilder.exception.UserAlreadyExistsException
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
        var exception = ""
        try {
            userService.save(request.name, request.email, request.password)
        } catch (e: UserAlreadyExistsException) {
            exception = e.message
        }
        return Auth.RegisterResponse.newBuilder()
                .setException(exception)
                .build()
    }

    @Override
    override suspend fun login(request: Auth.LoginRequest): Auth.LoginResponse {
        var exception = ""
        var jwt = ""
        try {
            val authentication = authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(request.email, request.password) // principal, credentials
            )
            jwt = jwtToken.generateJwtToken(authentication)
        } catch (e: Exception) {
            exception = "Invalid password or email."
        }

        return Auth.LoginResponse.newBuilder()
                .setToken(jwt)
                .setException(exception)
                .build()
    }
}
