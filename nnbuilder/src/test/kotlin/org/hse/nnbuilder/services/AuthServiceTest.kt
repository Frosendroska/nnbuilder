package org.hse.nnbuilder.services

import kotlinx.coroutines.test.runBlockingTest
import org.hse.nnbuilder.auth.JwtUtil
import org.hse.nnbuilder.exception.UserNotFoundException
import org.hse.nnbuilder.user.UserService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.jdbc.JdbcTestUtils

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
class AuthServiceTest {

    @Autowired
    lateinit var authService: AuthService

    @Autowired
    lateinit var userService: UserService

    @Autowired
    private lateinit var jwtToken: JwtUtil

    @Autowired
    private val jdbcTemplate: JdbcTemplate? = null

    @BeforeAll
    @AfterEach
            /**
             * Clear database
             */
    fun resetData() {
        try {
            JdbcTestUtils.deleteFromTables(jdbcTemplate!!, "users")
        } catch (e: Exception) {
            println("Something went wrong while cleaning database")
        }
    }

    @Test
    fun resisterTest() = runBlockingTest {
        // Prepare data
        val name = "Ivan"
        val email = "Ivan@mail.ru"
        val password = "ivanpassword"
        val request = createRegisterRequest(name, email, password)

        // Action
        authService.register(request)

        // Assert
        try {
            userService.findByEmail(email)
        } catch (e: UserNotFoundException) {
            fail(e.message)
        }
    }

    @Test
    fun registerAlreadyExistingUser() = runBlockingTest {
        // Prepare data
        val name = "name"
        val email = "email@mail.ru"
        val password = "password"
        val request = createRegisterRequest(name, email, password)

        // Action
        authService.register(request)
        val response = authService.register(request)

        // Assert
        assertTrue(response.exception.isNotEmpty())
    }

    @Test
    fun loginTest() = runBlockingTest {
        // Prepare data
        val name = "name"
        val email = "email@mail.ru"
        val password = "password"
        val registerRequest = createRegisterRequest(name, email, password)
        val loginRequest = createLoginRequest(email, password)

        // Action
        authService.register(registerRequest)
        val loginResponse = authService.login(loginRequest)

        //Prepare data
        val authentication = jwtToken.parseJwtToken(loginResponse.token)

        // Assert
        assertTrue(loginResponse.exception.isEmpty())
        assertNotNull(authentication)
        if (authentication != null) {
            assertEquals(authentication.name, email)
        }
    }

    @Test
    fun loginWithBadCredentials() = runBlockingTest {
        // Prepare data
        val name = "name"
        val email = "email@mail.ru"
        val invalidEmail = "invalidEmail"
        val password = "password"
        val invalidPassword = "invalidPassword"
        val registerRequest = createRegisterRequest(name, email, password)
        val invalidLoginRequest1 = createLoginRequest(invalidEmail, password)
        val invalidLoginRequest2 = createLoginRequest(email, invalidPassword)

        // Action
        authService.register(registerRequest)
        val loginResponse1 = authService.login(invalidLoginRequest1)
        val loginResponse2 = authService.login(invalidLoginRequest2)

        // Assert
        assertTrue(loginResponse1.exception.isNotEmpty())
        assertTrue(loginResponse1.token.isEmpty())
        assertTrue(loginResponse2.exception.isNotEmpty())
        assertTrue(loginResponse2.token.isEmpty())
    }

    @Test
    fun loginNotExistingUser() = runBlockingTest {
        // Prepare data
        val name = "name"
        val email = "email@mail.ru"
        val password = "password"
        val loginRequest = createLoginRequest(email, password)

        // Action
        val loginResponse = authService.login(loginRequest)

        // Assert
        assertTrue(loginResponse.exception.isNotEmpty())
    }

    private fun createRegisterRequest(name: String, email: String, password: String): Auth.RegisterRequest {
        val request = Auth.RegisterRequest.newBuilder()
                .setName(name)
                .setEmail(email)
                .setPassword(password)
                .build()

        return request
    }

    private fun createLoginRequest(email: String, password: String): Auth.LoginRequest {
        val request = Auth.LoginRequest.newBuilder()
                .setEmail(email)
                .setPassword(password)
                .build()

        return request
    }

    // TODO add opportunity to change password
}
