package com.yakidan.spring.test_project.forum.controller

import com.yakidan.spring.test_project.forum.model.dto.CommentDto
import com.yakidan.spring.test_project.forum.model.dto.TopicDto
import com.yakidan.spring.test_project.forum.entity.User
import com.yakidan.spring.test_project.forum.service.CommentService
import com.yakidan.spring.test_project.forum.service.TopicService
import com.yakidan.spring.test_project.forum.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.CurrentSecurityContext
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/topics")
class TopicController(
    @Autowired val topicService: TopicService,
    @Autowired val userService: UserService,
    @Autowired val commentService: CommentService,
) {

    @GetMapping()
    fun getAllTopics(
        @CurrentSecurityContext(expression = "authentication") auth: Authentication
    ): List<TopicDto> {
        return topicService.getAllTopicDto()
    }

    @GetMapping("/user")
    fun getTopicsByOwn(
        @CurrentSecurityContext(expression = "authentication") auth: Authentication
    ): List<TopicDto> {
        return topicService.getTopicsByOwn(getUserFromAuth(auth).id)
    }


    @GetMapping("/{id}")
    fun getTopicById(
        @PathVariable id: Long,
        @CurrentSecurityContext(expression = "authentication") authentication: Authentication
    ): TopicDto {
        return topicService.getTopicDtoById(id)
    }

    @PutMapping()
    fun updateTopic(
        @RequestBody topic: TopicDto,
        @CurrentSecurityContext(expression = "authentication") auth: Authentication
    ): TopicDto {
        if (topic.id == 0L)
            throw IllegalArgumentException("id is required in this method")

        return topicService.updateTopicDto(topic, getUserFromAuth(auth).id)
    }

    @PostMapping()
    fun addNewTopic(
        @RequestBody topic: TopicDto,
        @CurrentSecurityContext(expression = "authentication") auth: Authentication
    ): TopicDto? {
        return topicService.saveNewTopicDto(topic, getUserFromAuth(auth))
    }

    @DeleteMapping("/{id}")
    fun deleteTopic(
        @PathVariable id: Long,
        @CurrentSecurityContext(expression = "authentication") auth: Authentication
    ): String {
        return topicService.deleteTopicDtoById(id, getUserFromAuth(auth).id)
    }

    @GetMapping("/{topicId}/comments")
    fun getCommentsInTopic(
        @PathVariable topicId: Long,
        @CurrentSecurityContext(expression = "authentication") authn: Authentication
    ): List<CommentDto> {
        return commentService.getAllCommentDtoByTopicId(topicId)
    }

    @GetMapping("/{topicId}/comments/user")
    fun getCommentsInTopicByOwnUser(
        @PathVariable topicId: Long,
        @CurrentSecurityContext(expression = "authentication") auth: Authentication
    ): List<CommentDto> {
        return commentService.getAllCommentDtoByTopicIdAndUserId(
            topicId,
            getUserFromAuth(auth).id
        )
    }

    @PostMapping("/{topicId}/comments")
    fun createComment(
        @PathVariable topicId: Long,
        @RequestBody commentDto: CommentDto,
        @CurrentSecurityContext(expression = "authentication") auth: Authentication
    ): CommentDto {
        return commentService.saveNewCommentDto(
            commentDto,
            TopicDto.toTopic(topicService.getTopicDtoById(topicId)),
            getUserFromAuth(auth)
        )
    }

    @PutMapping("/{topicId}/comments")
    fun updateComment(
        @PathVariable topicId: Long,
        @RequestBody commentDto: CommentDto,
        @CurrentSecurityContext(expression = "authentication") auth: Authentication
    ): CommentDto {
        if (commentDto.id == 0L)
            throw IllegalArgumentException("topic id is required in this method")
        return commentService.updateCommentDto(
            commentDto,
            TopicDto.toTopic(topicService.getTopicDtoById(topicId)),
            getUserFromAuth(auth)
        )
    }

    @DeleteMapping("/{topicId}/comments/{id}")
    fun deleteComment(
        @PathVariable id: Long,
        @CurrentSecurityContext(expression = "authentication") auth: Authentication
    ): String {
        return commentService.deleteComment(
            id,
            getUserFromAuth(auth)
        )
    }


    private fun getUserFromAuth(auth: Authentication): User {
        val userDetails = auth.principal as UserDetails
        return userService.getUserByEmail(userDetails.username)
    }

}