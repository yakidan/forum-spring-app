package com.yakidan.spring.test_project.forum.security

import com.yakidan.spring.test_project.forum.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service

class UserDetailsServiceImpl(
    @Autowired private val userRepository: UserRepository
) : UserDetailsService {


    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email);
        if (user.isPresent)
            return SecurityUser.fromUser(user.get())
        throw EntityNotFoundException("entity with email = ${email} not found")
    }
}