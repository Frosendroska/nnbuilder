package org.hse.nnbuilder.auth

import io.grpc.Metadata
import io.grpc.ServerCall
import io.grpc.kotlin.CoroutineContextServerInterceptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ThreadContextElement
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import kotlin.coroutines.CoroutineContext

class SecurityCoroutineContext(
    private val securityContext: SecurityContext = SecurityContextHolder.getContext()
) : ThreadContextElement<SecurityContext?> {

    companion object Key : CoroutineContext.Key<SecurityCoroutineContext>

    override val key: CoroutineContext.Key<SecurityCoroutineContext> get() = Key

    override fun updateThreadContext(context: CoroutineContext): SecurityContext? {
        val previousSecurityContext = SecurityContextHolder.getContext()
        SecurityContextHolder.setContext(securityContext)
        return previousSecurityContext.takeIf { it.authentication != null }
    }

    override fun restoreThreadContext(context: CoroutineContext, oldState: SecurityContext?) {
        if (oldState == null) {
            SecurityContextHolder.clearContext()
        } else {
            SecurityContextHolder.setContext(oldState)
        }
    }
}

@GrpcGlobalServerInterceptor
class SecurityCoroutineContextInterceptor : CoroutineContextServerInterceptor() {
    // return a CoroutineContext in which to execute call and headers. Function will be called each time a call is executed.
    override fun coroutineContext(call: ServerCall<*, *>, headers: Metadata): CoroutineContext {
        return Dispatchers.Default + SecurityCoroutineContext()
    }
}
