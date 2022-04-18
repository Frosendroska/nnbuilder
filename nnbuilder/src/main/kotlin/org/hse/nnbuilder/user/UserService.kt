package org.hse.nnbuilder.user

import org.hse.nnbuilder.exception.InvalidPasswordException
import org.hse.nnbuilder.exception.UserNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun save(name: String, email: String, password: String): User {
        val user = User(name, email, password)
        return userRepository.save(user)
    }

    fun findByEmail(email: String): User {
        return userRepository.findByEmail(email) ?: throw UserNotFoundException("User with email $email is not found.")
    }

    fun findById(id: Long): Optional<User> {
        return userRepository.findById(id) ?: throw UserNotFoundException("User with id $id is not found.")
    }

    fun loginUser(email: String, password: String): User {
        val user =
            userRepository.findByEmail(email) ?: throw UserNotFoundException("User with email $email is not found.")
        if (!user.checkPassword(password)) {
            throw InvalidPasswordException("Invalid password!")
        }
        return user
    }
}