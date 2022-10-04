package com.yakidan.spring.test_project.forum.service

import com.yakidan.spring.test_project.forum.handler.exception.EntityNotFoundException
import com.yakidan.spring.test_project.forum.model.dto.TopicDto
import com.yakidan.spring.test_project.forum.entity.Topic
import com.yakidan.spring.test_project.forum.entity.User
import com.yakidan.spring.test_project.forum.repository.TopicRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service

@Service
class TopicService(
    @Autowired private val topicRepository: TopicRepository
) {
    fun getAllTopicDto(): List<TopicDto> {
        return getAllTopic()
            .stream()
            .map { topic -> TopicDto.toTopicDto(topic) }
            .toList()
    }

    fun getTopicDtoById(id: Long): TopicDto {
        return TopicDto.toTopicDto(getTopicById(id))
    }


    fun getTopicsByOwn(id: Long): List<TopicDto> {
        val topic = topicRepository.findAllByUserId(id,PageRequest.of(0,10))
        return topic
            .stream()
            .map { topic -> TopicDto.toTopicDto(topic) }
            .toList()
    }


    fun updateTopicDto(topicDto: TopicDto, userId: Long): TopicDto {
        return TopicDto.toTopicDto(updateTopic(topicDto, userId))
    }

    fun saveNewTopicDto(topic: TopicDto, user: User): TopicDto {
        return TopicDto.toTopicDto(saveTopic(topic, user))
    }

    fun deleteTopicDtoById(id: Long, userId: Long): String {
        return deleteTopicById(id, userId)
    }

    private fun getAllTopic(): List<Topic> {
        return topicRepository.findAllTopic(PageRequest.of(0,10))
    }

    private fun getTopicById(id: Long): Topic {
        val topicOptional = topicRepository.findById(id)
        if (topicOptional.isPresent) {
            return topicOptional.get()
        }
        throw EntityNotFoundException("Topic not found in repository with id = ${id}")
    }

    private fun updateTopic(topicDto: TopicDto, userId: Long): Topic {
        val topic = getTopicById(topicDto.id)
        validate(topic, userId)

        val newTopic = Topic(
            topicDto.id,
            topicDto.title ?: topic.title,
            topicDto.description ?: topic.description,
            topic.user
        )

        return topicRepository.save(newTopic)
    }


    private fun saveTopic(topicDto: TopicDto, user: User): Topic {
        val toTopic = TopicDto.toTopic(topicDto, user)
        return topicRepository.save(toTopic)
    }

    private fun deleteTopicById(id: Long, userId: Long): String {
        val topic = getTopicById(id)
        validate(topic, userId)
        topicRepository.delete(topic)

        return "Success delete topic with id ${id}"
    }


    private fun validate(topic: Topic, userId: Long) {
        if (topic.user.id != userId)
            throw AccessDeniedException("This topic isn't your")
    }
}