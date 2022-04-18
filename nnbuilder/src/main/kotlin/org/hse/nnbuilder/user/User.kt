package org.hse.nnbuilder.user

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Entity
@Table(name = "users")
class User() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Long = 0

    @Column
    private var name = ""

    @Column(unique = true)
    private var email = ""

    @Column
    @JsonIgnore
    private var password = ""
        get() = field
        set(value) {
            field = BCryptPasswordEncoder().encode(value)
        }

    constructor(name: String, email:String, password:String) : this() {
    this.name = name;
    this.email = email
    this.password = password
    }

    fun checkPassword(enteredPassword: String): Boolean {
        return BCryptPasswordEncoder().matches(enteredPassword, password)
    }

    fun getId() : Long {
        return id
    }

}