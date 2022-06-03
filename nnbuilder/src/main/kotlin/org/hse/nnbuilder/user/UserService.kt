package org.hse.nnbuilder.user

import org.hse.nnbuilder.exception.UserAlreadyExistsException
import org.hse.nnbuilder.exception.UserNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(
        private val userRepository: UserRepository
) {
    fun save(name: String, email: String, password: String): User {
        if (checkUserExists(email)) {
            throw UserAlreadyExistsException("User with email $email already exists.")
        }
        val user = User(name, email, password)
        return userRepository.save(user)
    }

    fun getById(id: Long): User {
        return userRepository.getById(id)
    }

    fun changeName(id: Long, newName: String) {
        val user = getById(id)
        user.changeName(newName)
        userRepository.save(user)
    }

    fun changePassword(id: Long, oldPassword: String, newPassword: String) {
        val user = getById(id)
        user.changePassword(oldPassword, newPassword)
        userRepository.save(user)
    }

    fun findByEmail(email: String): User {
        return userRepository.findByEmail(email) ?: throw UserNotFoundException("User with email $email is not found.")
    }

    fun checkUserExists(email: String): Boolean {
        return userRepository.findByEmail(email) != null
    }
}
