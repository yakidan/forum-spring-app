package com.yakidan.spring.test_project.forum.model.dto

import com.yakidan.spring.test_project.forum.entity.Topic
import com.yakidan.spring.test_project.forum.entity.User
import lombok.Data
import org.apache.logging.log4j.util.Strings

@Data
class TopicDto(
    val id: Long,
    val title: String?,
    val description: String?,
    val created_by: UserDto?,
) {
    companion object {
        fun toTopicDto(topic: Topic): TopicDto {
            return TopicDto(
                topic.id,
                topic.title,
                topic.description,
                UserDto.toUserDto(topic.user)
            )
        }

        fun toTopic(topic: TopicDto): Topic {
            return Topic(
                topic.id ?: 0,
                topic.title ?: Strings.EMPTY,
                topic.description ?: Strings.EMPTY,
                UserDto.toUser(topic.created_by ?: UserDto(10000, "", "", ""))
            )
        }

        fun toTopic(topic: TopicDto, user: User): Topic {
            return Topic(
                topic.id ?: 0,
                topic.title ?: Strings.EMPTY,
                topic.description ?: Strings.EMPTY,
                user
            )
        }

        class SimpleTopicDto(
            val id: Long = -1,
            val title: String? = null,
            val description: String? = null,
            val created_by: UserDto? = null
        ) {}
    }

    override fun toString(): String {
        return "TopicDto(id=$id, title=$title, description=$description, created_by=$created_by)"
    }


}