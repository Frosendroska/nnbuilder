package org.hse.nnbuilder.services

import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.context.SecurityContextHolder

@GrpcService
open class SumService : SumServiceGrpcKt.SumServiceCoroutineImplBase() {

    @Secured("ROLE_USER")
    override suspend fun getSum(request: Sum.GetSumRequest): Sum.GetSumResponse {
        println("user:" + SecurityContextHolder.getContext().authentication)

        return Sum.GetSumResponse.newBuilder()
            .setSum(request.lhs + request.rhs)
            .build()
    }
}
