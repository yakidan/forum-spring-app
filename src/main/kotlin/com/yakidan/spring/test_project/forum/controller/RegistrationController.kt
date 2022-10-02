package com.yakidan.spring.test_project.forum.controller

import com.yakidan.spring.test_project.forum.entity.User
import com.yakidan.spring.test_project.forum.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/register")
class RegistrationController(
    @Autowired val userService: UserService
) {

    @PostMapping()
    @Validated
    fun addNewUser(@Valid @RequestBody user: User, bindingResult: BindingResult): String {
        return userService.addNewUser(user)
    }
}