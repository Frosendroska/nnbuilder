package org.hse.nnbuilder.services

import kotlinx.coroutines.test.runBlockingTest
import org.hse.nnbuilder.user.User
import org.hse.nnbuilder.user.UserService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.jdbc.JdbcTestUtils
import javax.transaction.Transactional

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

    private val name = "Ivan3"
    private val email = "ivan3@mail.ru"
    private val password = "password"
    private lateinit var user: User

    @BeforeAll
    fun createTestUser() {
        resetData()

        // Prepare data
        if (!userService.checkUserExists(email)) { // TODO remove and add database cleaning
            user = userService.save(name, email, password)
        }
    }

    @BeforeEach
    fun setContext() {
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
            // JdbcTestUtils.deleteFromTables(jdbcTemplate!!, "neuralnetworks", "general_neural_network", "users")
        } catch (e: Exception) {
            println("Something went wrong while cleaning database")
        }
    }

    @Test
    fun getNameTest() = runBlockingTest {
        // Prepare data
        val expectedName = "Ivan3"
        val request = UserAccount.GetNameRequest.newBuilder().build()

        // Action
        val response = userAccountService.getName(request)

        // Assert
        assertEquals(expectedName, response.name)
    }

    @Test
    fun getEmailTest() = runBlockingTest {
        // Prepare data
        val expectedEmail = "ivan3@mail.ru"
        val request = UserAccount.GetEmailRequest.newBuilder().build()

        // Action
        val response = userAccountService.getEmail(request)

        // Assert
        assertEquals(expectedEmail, response.email)
    }

    @Test
    fun changeNameTest() = runBlockingTest {
        // Prepare data
        val newName = "Olga"
        val oldName = "Ivan3"
        val request1 = UserAccount.ChangeNameRequest.newBuilder()
            .setNewName(newName)
            .build()
        val request2 = UserAccount.ChangeNameRequest.newBuilder()
            .setNewName(oldName)
            .build()

        // Action
        userAccountService.changeName(request1)

        // Assertion
        var user = userService.findByEmail(email)
        assertEquals(newName, user.getName())

        // Action
        userAccountService.changeName(request2)

        // Assertion
        user = userService.findByEmail(email)
        assertEquals(oldName, user.getName())
    }

    @Test
    fun changePasswordTest() = runBlockingTest {
        // Prepare data
        val newPassword = "neeeew"
        val oldPassword = "password"
        val request1 = UserAccount.ChangePasswordRequest.newBuilder()
            .setNewPassword(newPassword)
            .setOldPassword(oldPassword)
            .build()
        val request2 = UserAccount.ChangePasswordRequest.newBuilder()
            .setNewPassword(oldPassword)
            .setOldPassword(newPassword)
            .build()

        // Action
        userAccountService.changePassword(request1)

        // Assertion
        var user = userService.findByEmail(email)
        assertTrue(BCryptPasswordEncoder().matches(newPassword, user.getPassword()))

        // Action
        userAccountService.changePassword(request2)

        // Assertion
        user = userService.findByEmail(email)
        assertTrue(BCryptPasswordEncoder().matches(oldPassword, user.getPassword()))
    }
}
