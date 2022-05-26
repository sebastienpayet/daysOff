package com.archeon.daysoff.business.useCase.user.createAUser

import com.archeon.daysoff.business.domain.user.Role
import com.archeon.daysoff.business.useCase.Command

class CreateUserCommand(
	val lastname: String,
	val firstname: String,
	val hasMinorChild: Boolean,
	val teamIds: Set<String>,
	val email: String,
	val credential: String,
	val role: Role
) : Command