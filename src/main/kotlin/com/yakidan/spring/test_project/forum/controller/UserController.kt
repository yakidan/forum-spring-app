package com.yakidan.spring.test_project.forum.controller

import com.yakidan.spring.test_project.forum.entity.User
import com.yakidan.spring.test_project.forum.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    @Autowired val userService: UserService
) {

    @GetMapping()
    fun getAllUser(): List<User> {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): User {
        return userService.getUserById(id)
    }

    @PostMapping()
    fun createNewUser(@RequestBody user: User): String {
        return userService.addNewUser(user)
    }
}