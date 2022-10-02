package com.yakidan.spring.test_project.forum.controller

import com.yakidan.spring.test_project.forum.model.dto.CommentDto
import com.yakidan.spring.test_project.forum.entity.User
import com.yakidan.spring.test_project.forum.service.CommentService
import com.yakidan.spring.test_project.forum.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.CurrentSecurityContext
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("/api/v1/comments")
class CommentController(
    @Autowired val commentService: CommentService,
    @Autowired val userService: UserService
) {

    @GetMapping("/user")
    fun getAllCommentDtoByOwnUser(
        @CurrentSecurityContext(expression = "authentication") auth: Authentication
    ): List<CommentDto> {
        return commentService.getAllCommentDtoByUserId(
            getUserFromAuth(auth).id
        )
    }

    @GetMapping("/{id}")
    fun getCommentDto(@PathVariable id: Long): CommentDto {
        return commentService.getCommentDtoById(id)
    }

    private fun getUserFromAuth(auth: Authentication): User {
        val userDetails = auth.principal as UserDetails
        return userService.getUserByEmail(userDetails.username)
    }
}