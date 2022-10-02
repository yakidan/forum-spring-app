package com.yakidan.spring.test_project.forum.handler

import com.yakidan.spring.test_project.forum.handler.exception.EntityNotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(
        value = [EntityNotFoundException::class,
            AccessDeniedException::class, IllegalArgumentException::class]
    )
    protected fun handleConflict(
        ex: RuntimeException,
        request: WebRequest
    ): ResponseEntity<Any> {
        return handleExceptionInternal(
            ex, ex.message,
            HttpHeaders(), HttpStatus.BAD_REQUEST, request
        )
    }

    @ExceptionHandler
    fun handleException(exception: RuntimeException): ResponseEntity<RuntimeException> {
        return ResponseEntity(exception, HttpStatus.BAD_REQUEST)
    }
}