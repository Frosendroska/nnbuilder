package org.hse.nnbuilder.services

import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.beans.factory.annotation.Autowired

@GrpcService
class UserAccountService : UserAccountServiceGrpcKt.UserAccountServiceCoroutineImplBase() {

    @Autowired
    private lateinit var util: Util

    @Override
    override suspend fun getName(request: UserAccount.GetNameRequest): UserAccount.GetNameResponse {
        val name = util.getUser().getName()
        return UserAccount.GetNameResponse.newBuilder()
                .setName(name)
                .build()
    }

    @Override
    override suspend fun getEmail(request: UserAccount.GetEmailRequest): UserAccount.GetEmailResponse {
        val email = util.getUser().getEmail()
        return UserAccount.GetEmailResponse.newBuilder()
                .setEmail(email)
                .build()
    }
}