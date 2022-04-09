package org.hse.nnbuilder.user

import javax.persistence.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Entity
@Table(name = "users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column
    var name = ""

    @Column(unique = true)
    var email = ""

    @Column
    var password = ""
        get() = field
        set(value) {
            field = BCryptPasswordEncoder().encode(value)
        }

    fun checkPassword(enteredPassword: String): Boolean {
        return BCryptPasswordEncoder().matches(enteredPassword, password)
    }

}