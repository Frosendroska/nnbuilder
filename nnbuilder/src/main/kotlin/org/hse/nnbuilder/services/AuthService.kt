package org.hse.nnbuilder.services

import net.devh.boot.grpc.server.service.GrpcService
import org.hse.nnbuilder.user.UserService
import org.springframework.beans.factory.annotation.Autowired

@GrpcService
class AuthService : AuthServiceGrpcKt.AuthServiceCoroutineImplBase() {

    @Autowired
    private lateinit var userService: UserService

    override suspend fun register(request: Auth.RegisterRequest): Auth.RegisterResponse {
        userService.save(request.name, request.email, request.password)
        return Auth.RegisterResponse.newBuilder().build();
    }
}