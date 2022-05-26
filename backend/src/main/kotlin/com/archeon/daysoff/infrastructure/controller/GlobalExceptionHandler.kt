package com.archeon.daysoff.infrastructure.controller

import com.fasterxml.jackson.core.JsonProcessingException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
@ResponseBody
class GlobalExceptionHandler {

	private val logger: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

	@ExceptionHandler
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	fun illegalArgumentException(e:IllegalArgumentException): String? {
		logger.info(e.stackTraceToString())
		return e.message
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	fun illegalArgumentException(e: JsonProcessingException): String? {
		logger.info(e.stackTraceToString())
		return e.message
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	fun illegalArgumentException(e:IllegalStateException): String? {
		logger.error(e.stackTraceToString())
		return e.message
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	fun illegalArgumentException(e:IllegalAccessException): String? {
		logger.info(e.stackTraceToString())
		return e.message
	}
}
