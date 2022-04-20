package org.hse.nnbuilder.auth

import org.hse.nnbuilder.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class UserDetailsServiceImpl : UserDetailsService {

    @Autowired
    lateinit var userService: UserService

    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userService.findByEmail(username)
        return User.builder()
            .username(user.getEmail())
            .password(user.getPassword())
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(false)
            .authorities("ROLE_USER")
            .build()
    }
}
