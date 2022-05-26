package com.archeon.daysoff.business.useCase.user.listObfuscatedUsers

import com.archeon.daysoff.business.domain.user.ObfuscatedUser
import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.service.UserService
import com.archeon.daysoff.business.useCase.UseCase

class ListAllObfuscatedUsers(
	private val authorizationGateway: AuthorizationGateway,
	private val userService: UserService
) : UseCase<ListObfuscatedUsersCommand, Set<ObfuscatedUser>> {
	override fun handle(command: ListObfuscatedUsersCommand): Set<ObfuscatedUser> {
		authorizationGateway.checkAuthorization(this, command)
		return userService.securedListAllUsers()
	}
}
