package org.hse.nnbuilder.services

import kotlinx.coroutines.test.runBlockingTest
import org.hse.nnbuilder.nn.store.NeuralNetworkService
import org.hse.nnbuilder.user.User
import org.hse.nnbuilder.user.UserService
import org.hse.nnbuilder.version_controller.GeneralNeuralNetworkService
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.jdbc.JdbcTestUtils

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
class NNVersionServiceTest {
    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var nnModificationService: NNModificationService

    @Autowired
    private lateinit var generalNeuralNetworkService: GeneralNeuralNetworkService

    @Autowired
    private lateinit var neuralNetworkService: NeuralNetworkService

    @Autowired
    private lateinit var nnVersionService: NNVersionService

    @Autowired
    private val jdbcTemplate: JdbcTemplate? = null

    private lateinit var user: User

    @BeforeAll
    fun createTestUser() {
        resetUserData()

        //Prepare data
        val name = "Ivan"
        val email = "ivan@mail.ru"
        val password = "password"

        user = userService.save(name, email, password)

        val context: SecurityContext = SecurityContextHolder.createEmptyContext()
        val authentication: Authentication = TestingAuthenticationToken(email, password, "ROLE_USER")
        context.authentication = authentication

        SecurityContextHolder.setContext(context)
    }

    @AfterEach
            /**
             * Clear database
             */
    fun resetData() {
        try {
            JdbcTestUtils.deleteFromTables(jdbcTemplate!!, "neuralnetwork", "general_neural_network")
        } catch (e: Exception) {
            println("Something went wrong while cleaning database")
        }
    }

    @AfterAll
    fun resetUserData() {
        resetData()
        try {
            JdbcTestUtils.deleteFromTables(jdbcTemplate!!, "users")
        } catch (e: Exception) {
            println("Something went wrong while cleaning database")
        }
    }

    @Test
    fun correctGeneralNNCreating() = runBlockingTest {
        //Action
        val nnId = nnModificationService.creatennForUser(user.getEmail(), Nnmodification.NetworkType.FF)
        val nnVersion = neuralNetworkService.getById(nnId)
        val project = generalNeuralNetworkService.getByIdOfNNVersion(nnId)  //general neural network

        //Assert
        assertTrue(project.owner.getId() == user.getId())
        assertTrue(nnVersion.generalNeuralNetwork.getId() == project.getId())
    }

    @Test
    fun makeSnapshotTest() = runBlockingTest {
        //Prepare data
        val nnId = nnModificationService.creatennForUser(user.getEmail(), Nnmodification.NetworkType.FF)
        val originalNNStored = neuralNetworkService.getById(nnId)
        val originalNN = originalNNStored.neuralNetwork
        val request = Nnversion.makeNNSnapshotRequest.newBuilder().setNnId(nnId).build()

        //Action
        val response = nnVersionService.makeNNSnapshot(request)
        val snapshotId = response.nnId
        val snapshotNNStored = neuralNetworkService.getById(snapshotId)
        val snapshotNN = snapshotNNStored.neuralNetwork

        //Assert
        assertEquals(originalNNStored.generalNeuralNetwork.getId(), snapshotNNStored.generalNeuralNetwork.getId())
        assertEquals(originalNN.nnType, snapshotNN.nnType)
        assertEquals(originalNN.layers, snapshotNN.layers)
        assertEquals(originalNN.learningRate, snapshotNN.learningRate)
        assertEquals(originalNN.defaultNumberOfLayers, snapshotNN.defaultNumberOfLayers)
    }
}