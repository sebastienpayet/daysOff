package com.archeon.daysoff.business.useCase

import com.archeon.daysoff.business.domain.user.User

interface UseCaseAuthorization<T: Command> {
	fun doCheck(user: User, command: T)
}