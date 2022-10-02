package com.yakidan.spring.test_project.forum.repository

import com.yakidan.spring.test_project.forum.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CommentRepository : JpaRepository<Comment, Long> {


    @Query("select c from Comment c where c.topic.id=?1")
    fun findAllByTopicId(topicId: Long): List<Comment>

    @Query("select c from Comment c where c.topic.id=?1 and c.user.id=?2 ")
    fun findAllByTopicIdAndUserId(topicId: Long, userId: Long): List<Comment>

    @Query("select c from Comment c where c.user.id=?1 ")
    fun findAllByUserId(userId: Long): List<Comment>

}