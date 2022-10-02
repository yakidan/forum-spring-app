package com.yakidan.spring.test_project.forum.repository

import com.yakidan.spring.test_project.forum.entity.Topic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.*
import javax.transaction.Transactional

@Transactional
interface TopicRepository : JpaRepository<Topic, Long> {

    @Query("select t from Topic t")
    fun findAllTopics(): List<Topic>

    @Query("select t from Topic t where t.id=?1")
    override fun findById(id: Long): Optional<Topic>

    @Modifying
    @Query("update Topic t set t.title=?2, t.description=?3 where t.id=?1")
    fun updateTopic(id: Long, title: String, description: String): Int

    @Query("select t from Topic t where t.user.id=?1")
    fun findAllByUserId(id: Long): List<Topic>

}