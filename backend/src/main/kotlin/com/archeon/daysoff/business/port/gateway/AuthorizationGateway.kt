package com.archeon.daysoff.business.port.gateway

import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.service.LeaveRequestService
import com.archeon.daysoff.business.useCase.Command
import com.archeon.daysoff.business.useCase.UseCase

interface AuthorizationGateway {

	val authenticationGateway: AuthenticationGateway
	val userRepository: UserRepository
	val leaveRequestService: LeaveRequestService

	@Throws(IllegalAccessException::class)
	fun checkAuthorization(useCase: UseCase<*, *>, command: Command)
}
