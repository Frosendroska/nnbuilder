package org.hse.nnbuilder.services

import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SumServiceTest {

    private val sumService = SumService()

    @Test
    fun getSum() = runBlockingTest {
        val request = Sum.GetSumRequest.newBuilder()
            .setLhs(2)
            .setRhs(3)
            .build()

        assertThat(sumService.getSum(request).sum).isEqualTo(5)
    }
}
