package org.hse.nnbuilder.services

import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class SumService : SumServiceGrpcKt.SumServiceCoroutineImplBase() {

    override suspend fun getSum(request: Sum.GetSumRequest): Sum.GetSumResponse {
        return Sum.GetSumResponse.newBuilder()
            .setSum(request.lhs + request.rhs)
            .build()
    }
}
