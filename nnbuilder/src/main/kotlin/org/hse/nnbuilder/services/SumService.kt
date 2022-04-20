package org.hse.nnbuilder.services

import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.security.core.context.SecurityContextHolder

@GrpcService
class SumService : SumServiceGrpcKt.SumServiceCoroutineImplBase() {

    override suspend fun getSum(request: Sum.GetSumRequest): Sum.GetSumResponse {
        println("user:" + SecurityContextHolder.getContext().authentication)

        return Sum.GetSumResponse.newBuilder()
            .setSum(request.lhs + request.rhs)
            .build()
    }
}
