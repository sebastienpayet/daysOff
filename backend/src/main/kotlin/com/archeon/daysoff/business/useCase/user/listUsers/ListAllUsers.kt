package com.archeon.daysoff.business.useCase.user.listUsers

import com.archeon.daysoff.business.domain.user.User
import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.service.UserService
import com.archeon.daysoff.business.useCase.UseCase

class ListAllUsers(
	private val authorizationGateway: AuthorizationGateway,
	private val userService: UserService
) : UseCase<ListAllUsersCommand, Set<User>> {
	override fun handle(command: ListAllUsersCommand): Set<User> {
		authorizationGateway.checkAuthorization(this, command)
		return userService.listAllUsers()
	}
}
