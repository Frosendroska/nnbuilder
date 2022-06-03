package org.hse.nnbuilder.services

import org.hse.nnbuilder.user.User
import org.hse.nnbuilder.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class Util {
    @Autowired
    private lateinit var userService: UserService

    fun getUser(): User {
        val userEmail = SecurityContextHolder.getContext().authentication.name
        return userService.findByEmail(userEmail)
    }
}
