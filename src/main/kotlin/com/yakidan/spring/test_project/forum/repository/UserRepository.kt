package com.yakidan.spring.test_project.forum.repository

import com.yakidan.spring.test_project.forum.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Transactional
interface UserRepository : JpaRepository<User, Long> {
    @Query("select u from User u  where u.email=?1")
    fun findByEmail(email: String): Optional<User>

    @Query("select u from User u ")
    fun findAllUsers(): List<User>

    @Query("select u from User u where u.id=?1 ")
    override fun findById(id: Long): Optional<User>
}