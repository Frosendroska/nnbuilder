package org.hse.nnbuilder.services

import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class UserService: UserServiceGrpcKt.UserServiceCoroutineImplBase() {

    override suspend fun getUserById(request: UserServiceOuterClass.GetUserByIdRequest): UserServiceOuterClass.User {
        return UserServiceOuterClass.User.getDefaultInstance()
    }
}