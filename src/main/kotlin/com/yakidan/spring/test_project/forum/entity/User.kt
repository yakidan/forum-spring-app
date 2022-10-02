package com.yakidan.spring.test_project.forum.entity

import lombok.Data
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank


@Data
@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long,

    @Email
    @Column(name = "email")
    val email: String,

    @Column(name = "first_name")
    @NotBlank(message = "firstName is mandatory")
    val firstName: String,

    @Column(name = "last_name")
    @NotBlank
    val lastName: String,

    @Column(name = "password")
    @NotBlank
    val password: String,

    ) {
    constructor(id: Long, email: String, firstName: String, lastName: String) :
            this(id, email, firstName, lastName, "");

    override fun toString(): String {
        return "User(id=$id, email='$email', firstName='$firstName', lastName='$lastName')"
    }
}