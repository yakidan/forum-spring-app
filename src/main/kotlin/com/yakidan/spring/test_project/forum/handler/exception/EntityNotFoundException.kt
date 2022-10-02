package com.yakidan.spring.test_project.forum.handler.exception

class EntityNotFoundException(
    override val message: String?
) : RuntimeException(message) {
}