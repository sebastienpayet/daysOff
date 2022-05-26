package com.archeon.daysoff.infrastructure.gateway.authentication.token

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
class Token(
	var id: String = "",
	var value: String = "",
	var lifetime: Int = 0,
	var inactivityTime: Int = 0,
	var userId: String = "",
	var userRole: String = "",
	var nonce: LocalDateTime? = null,
	@JsonIgnore var lastTimeUsed: LocalDateTime? = null

)
