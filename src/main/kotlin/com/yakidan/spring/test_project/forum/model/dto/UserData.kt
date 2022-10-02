package com.yakidan.spring.test_project.forum.model.dto

import lombok.Data

@Data
class UserData(
    val id: Long,
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
) {
}