package com.yakidan.spring.test_project.forum.repository

import com.yakidan.spring.test_project.forum.entity.Topic
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*
import javax.transaction.Transactional

@Transactional
interface TopicRepository : JpaRepository<Topic, Long> {

    @Query("select t from Topic t")
    fun findAllTopic(): List<Topic>

    @Query("select t from Topic t")
    fun findAllTopic(pageable: Pageable): List<Topic>

    @Query("select t from Topic t where t.id=?1")
    override fun findById(id: Long): Optional<Topic>

    @Query("select t from Topic t where t.user.id=?1")
    fun findAllByUserId(id: Long): List<Topic>

    @Query("select t from Topic t where t.user.id=?1")
    fun findAllByUserId(id: Long, pageable: Pageable): List<Topic>
}