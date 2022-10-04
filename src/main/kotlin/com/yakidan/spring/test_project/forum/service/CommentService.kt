package com.yakidan.spring.test_project.forum.service

import com.yakidan.spring.test_project.forum.entity.Comment
import com.yakidan.spring.test_project.forum.entity.Topic
import com.yakidan.spring.test_project.forum.entity.User
import com.yakidan.spring.test_project.forum.handler.exception.EntityNotFoundException
import com.yakidan.spring.test_project.forum.model.dto.CommentDto
import com.yakidan.spring.test_project.forum.repository.CommentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Component

@Component
class CommentService(
    @Autowired private val commentRepository: CommentRepository
) {

    fun getAllCommentDtoByUserId(userId: Long): List<CommentDto> {
        return getAllCommentByUserId(userId)
            .stream()
            .map { comment -> CommentDto.toCommentDto(comment) }
            .toList()
    }

    fun getAllCommentDtoByTopicId(topicId: Long): List<CommentDto> {
        return getCommentsByTopicId(topicId)
            .stream()
            .map { comment -> CommentDto.toCommentDto(comment) }
            .toList()
    }

    fun getAllCommentDtoByTopicIdAndUserId(topicId: Long, userId: Long): List<CommentDto> {
        return getAllCommentByTopicIdAndUserId(topicId, userId)
            .stream()
            .map { comment -> CommentDto.toCommentDto(comment) }
            .toList()

    }

    fun getCommentDtoById(id: Long): CommentDto {
        return CommentDto.toCommentDto(getCommentById(id))
    }

    fun saveNewCommentDto(
        commentDto: CommentDto,
        topic: Topic,
        user: User
    ): CommentDto {
        return CommentDto.toCommentDto(saveComment(commentDto, topic, user))
    }

    fun updateCommentDto(commentDto: CommentDto, topic: Topic, user: User): CommentDto {
        return CommentDto.toCommentDto(updateComment(commentDto, topic, user))
    }

    fun deleteComment(id: Long, user: User): String {
        val comment = getCommentById(id)
        validate(comment, user.id)
        commentRepository.delete(comment)

        return "Success delete comment with id = ${id}"
    }

    private fun getCommentById(id: Long): Comment {
        val commentOptional = commentRepository.findById(id)
        if (commentOptional.isPresent)
            return commentOptional.get()
        throw EntityNotFoundException("comment isn't find with id = ${id}")
    }

    private fun getCommentsByTopicId(topicId: Long): List<Comment> {
        return commentRepository.findAllByTopicId(topicId, PageRequest.of(0, 10))

    }

    private fun getAllCommentByTopicIdAndUserId(topicId: Long, userId: Long): List<Comment> {
        return commentRepository.findAllByTopicIdAndUserId(topicId, userId, PageRequest.of(0, 10))
    }

    private fun saveComment(
        commentDto: CommentDto,
        topic: Topic,
        user: User
    ): Comment {
        val comment = CommentDto.toComment(commentDto, topic, user)
        return commentRepository.save(comment)
    }

    private fun updateComment(commentDto: CommentDto, topic: Topic, user: User): Comment {
        val comment = getCommentById(commentDto.id)
        validate(comment, user.id)

        val newComment = Comment(
            commentDto.id,
            commentDto.text ?: comment.text,
            topic,
            user
        )

        return commentRepository.save(newComment)
    }


    private fun getAllCommentByUserId(userId: Long): List<Comment> {
        return commentRepository.findAllByUserId(userId, PageRequest.of(0, 10))
    }

    private fun validate(comment: Comment, userId: Long) {
        if (comment.user.id != userId)
            throw AccessDeniedException("This comment isn't your")
    }

}
