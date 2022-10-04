package com.yakidan.spring.test_project.forum.service

import com.yakidan.spring.test_project.forum.handler.exception.EntityNotFoundException
import com.yakidan.spring.test_project.forum.entity.User
import com.yakidan.spring.test_project.forum.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired private val userRepository: UserRepository,
    @Autowired val passwordEncoder: PasswordEncoder
) {

    fun getAllUsers(): List<User> {
        return userRepository.findAllUser(PageRequest.of(0, 10))
    }

    fun getUserByEmail(email: String): User {
        val userOptional = userRepository.findByEmail(email)
        if (userOptional.isPresent)
            return userOptional.get()
        throw EntityNotFoundException("entity with email = ${email} not found")
    }

    fun getUserById(id: Long): User {
        val optionalUser = userRepository.findById(id)
        if (optionalUser.isPresent) {
            return optionalUser.get()
        }
        throw EntityNotFoundException("User not found in user repository with id =  ${id}")
    }

    fun addNewUser(user: User): String {
        val newUser = User(
            user.id,
            user.email,
            user.firstName,
            user.lastName,
            passwordEncoder.encode(user.password)
        )
        userRepository.save(newUser)
        return "Success registration"
    }

}