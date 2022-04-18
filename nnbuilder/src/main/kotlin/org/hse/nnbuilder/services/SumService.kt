package org.hse.nnbuilder.services

import net.devh.boot.grpc.server.service.GrpcService
import org.hse.nnbuilder.auth.Constants

@GrpcService
class SumService : SumServiceGrpcKt.SumServiceCoroutineImplBase() {

    override suspend fun getSum(request: Sum.GetSumRequest): Sum.GetSumResponse {
        //clientId -- это получаемый id пользователя из передаваемого токена
        //сейчас наш запрос в принципе не должен вызываться, потому что Status.UNAUTHENTICATED в Interceptor'e.
        // Если раскоментировать нижние строки, то он перестает вызываться, почему-то......

//        val clientId: String = Constants.CLIENT_ID_CONTEXT_KEY.get()
//        println("Processing request from $clientId")


        return Sum.GetSumResponse.newBuilder()
            .setSum(request.lhs + request.rhs)
            .build()
    }
}
