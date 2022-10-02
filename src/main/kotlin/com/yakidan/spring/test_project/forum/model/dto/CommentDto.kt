package com.yakidan.spring.test_project.forum.model.dto

import com.yakidan.spring.test_project.forum.entity.Comment
import com.yakidan.spring.test_project.forum.entity.Topic
import com.yakidan.spring.test_project.forum.entity.User
import lombok.Data

@Data
class CommentDto(
    val id: Long,
    val text: String?,
    val topic: TopicDto?,
    val created_by: UserDto?
) {
    companion object {
        fun toCommentDto(comment: Comment): CommentDto {
            return CommentDto(
                comment.id,
                comment.text,
                TopicDto.toTopicDto(comment.topic),
                UserDto.toUserDto(comment.user)
            )
        }

        fun toComment(commentDto: CommentDto): Comment {
            return Comment(
                commentDto.id,
                commentDto.text ?: "",
                TopicDto.toTopic(commentDto.topic ?: TopicDto(-1, "", "", commentDto.created_by)),
                UserDto.toUser(commentDto.created_by ?: UserDto(-1, "", "", ""))
            )
        }

        fun toComment(commentDto: CommentDto, topic: Topic, user: User): Comment {
            return Comment(
                commentDto.id,
                commentDto.text ?: "",
                topic,
                user
            )
        }
    }

    override fun toString(): String {
        return "CommentDto(id=$id, title=$text, topic=$topic, created_by=$created_by)"
    }

}