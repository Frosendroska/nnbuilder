package org.hse.nnbuilder.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun deleteUserByEmail(email: String)
}