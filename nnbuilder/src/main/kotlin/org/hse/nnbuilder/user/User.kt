package org.hse.nnbuilder.user

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hse.nnbuilder.version_controller.GeneralNeuralNetwork
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.OrderBy
import javax.persistence.Table

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
        set(value) {
            field = BCryptPasswordEncoder().encode(value)
        }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner", orphanRemoval = true)
    @OrderBy("id ASC")
    val projects: Set<GeneralNeuralNetwork> = LinkedHashSet()

    constructor(name: String, email: String, password: String) : this() {
        this.name = name
        this.email = email
        this.password = password
    }

    fun getId(): Long {
        return id
    }

    fun getEmail(): String {
        return email
    }

    fun getName(): String {
        return name
    }

    fun getPassword(): String {
        return password
    }

    fun changeName(newName: String) {
        name = newName
    }

    fun changePassword(oldPassword: String, newPassword: String) {
        if (BCryptPasswordEncoder().matches(oldPassword, password)) {
            password = newPassword
        } else {
            throw IllegalArgumentException("Invalid credentials!")
        }
    }
}
