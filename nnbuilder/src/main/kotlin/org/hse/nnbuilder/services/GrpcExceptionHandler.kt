package org.hse.nnbuilder.services

import io.grpc.ForwardingServerCallListener
import io.grpc.ServerCall
import io.grpc.ServerCallHandler
import io.grpc.ServerInterceptor
import io.grpc.Status
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor
import org.hse.nnbuilder.services.errors.NotFoundError

@GrpcGlobalServerInterceptor
class GrpcExceptionHandler : ServerInterceptor {
    override fun <ReqT : Any?, RespT : Any?> interceptCall(
        call: ServerCall<ReqT, RespT>,
        headers: io.grpc.Metadata,
        next: ServerCallHandler<ReqT, RespT>
    ): ServerCall.Listener<ReqT> {
        val delegate = next.startCall(call, headers)
        return object : ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(delegate) {

            override fun onHalfClose() {
                try {
                    super.onHalfClose()
                } catch (e: NotFoundError) {
                    call.close(
                        Status.NOT_FOUND
                            .withCause(e)
                            .withDescription(e.message),
                        io.grpc.Metadata()
                    )
                }
            }
        }
    }
}
