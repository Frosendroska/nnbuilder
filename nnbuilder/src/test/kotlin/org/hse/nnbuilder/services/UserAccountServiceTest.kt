package org.hse.nnbuilder.services

import javax.transaction.Transactional
import kotlinx.coroutines.test.runBlockingTest
import org.hse.nnbuilder.user.User
import org.hse.nnbuilder.user.UserService
import org.junit.jupiter.api.Assertions.assertEquals
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
class UserAccountServiceTest {
    @Autowired
    private lateinit var userAccountService: UserAccountService

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private val jdbcTemplate: JdbcTemplate? = null

    private lateinit var user: User

    @BeforeAll
    fun createTestUser() {
        resetData()

        // Prepare data
        val name = "Ivan3"
        val email = "ivan3@mail.ru"
        val password = "password"

        if (!userService.checkUserExists(email)) {  //TODO remove and add database cleaning
            user = userService.save(name, email, password)
        }

        val context: SecurityContext = SecurityContextHolder.createEmptyContext()
        val authentication: Authentication = TestingAuthenticationToken(email, password, "ROLE_USER")
        context.authentication = authentication

        SecurityContextHolder.setContext(context)
    }

    /**
     * Clear database
     */
    @Transactional
    fun resetData() {
        try {
            JdbcTestUtils.dropTables(jdbcTemplate!!)
            //JdbcTestUtils.deleteFromTables(jdbcTemplate!!, "neuralnetworks", "general_neural_network", "users")
        } catch (e: Exception) {
            println("Something went wrong while cleaning database")
        }
    }

    @Test
    fun getNameTest() = runBlockingTest {
        //Prepare data
        val expectedName = "Ivan3"
        val request = UserAccount.GetNameRequest.newBuilder().build()

        //Action
        val response = userAccountService.getName(request)

        //Assert
        assertEquals(expectedName, response.name)
    }

    @Test
    fun getEmailTest() = runBlockingTest {
        //Prepare data
        val expectedEmail = "ivan3@mail.ru"
        val request = UserAccount.GetEmailRequest.newBuilder().build()

        //Action
        val response = userAccountService.getEmail(request)

        //Assert
        assertEquals(expectedEmail, response.email)
    }

}