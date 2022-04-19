//package org.hse.nnbuilder.services
//
//import org.hse.nnbuilder.services.FastForwardNN
//import kotlinx.coroutines.test.runBlockingTest
//import org.assertj.core.api.Assertions.assertThat
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.TestInstance
//import org.springframework.beans.factory.annotation.Autowired
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class NNCreatingTests {
//
//    @Test
//    fun buildDefaultFastForwardNNTest() = runBlockingTest {
//        val nn = FastForwardNN.buildDefaultFastForwardNN()
//
//        // TYPE
//        assertThat(nn.getLayers()[0].layerType).isEqualTo(Layer.LayerType.InputCell)
//        assertThat(nn.getLayers()[1].layerType).isEqualTo(Layer.LayerType.OutputCell)
//
//        // ACTIVATION FUNCTION
//        assertThat(nn.getLayers()[0].activationFunction).isEqualTo(Layer.ActivationFunction.None)
//        assertThat(nn.getLayers()[1].activationFunction).isEqualTo(Layer.ActivationFunction.None)
//
//        // SIZE
//        assertThat(nn.getLayers().size).isEqualTo(nn.defaultNumberOfLayers)
//
//        // NUMBER OF NEURONS
//        assertThat(nn.getLayers()[0].neurons).isEqualTo(2)
//        assertThat(nn.getLayers()[1].neurons).isEqualTo(1)
//    }
//}
