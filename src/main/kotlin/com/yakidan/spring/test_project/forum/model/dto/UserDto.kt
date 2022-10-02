package com.yakidan.spring.test_project.forum.model.dto

import com.yakidan.spring.test_project.forum.entity.User
import lombok.Data

@Data
class UserDto(
    val id: Long,
    val email: String,
    val firstName: String,
    val lastName: String,
) {
    companion object {
        fun toUserDto(user: User): UserDto {
            return UserDto(
                user.id,
                user.email,
                user.firstName,
                user.lastName
            )
        }

        fun toUser(user: UserDto): User {
            return User(
                user.id,
                user.email,
                user.firstName,
                user.lastName,
                "[Private]"
            )
        }
    }

    override fun toString(): String {
        return "UserDto(id=$id, email='$email', firstName='$firstName', lastName='$lastName')"
    }

}