package org.hse.nnbuilder.user

import org.springframework.stereotype.Service

@Service
class UserService(
        private val userRepository: UserRepository
) {
    fun save(user: User): User {
        return userRepository.save(user)
    }
}