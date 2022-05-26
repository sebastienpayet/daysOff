package com.archeon.daysoff.business.useCase.user.deleteAUser

import com.archeon.daysoff.business.domain.user.User
import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.service.UserService
import com.archeon.daysoff.business.useCase.UseCase
import com.archeon.daysoff.business.util.ErrorUtil.UNKNOWN_USER

class DeleteAUser(
	private val userRepository: UserRepository,
	private val authorizationGateway: AuthorizationGateway,
	private val userService: UserService
) : UseCase<DeleteUserCommand, User> {

	override fun handle(command: DeleteUserCommand): User {
		authorizationGateway.checkAuthorization(this, command)

		// check that user exists
		val currentUser: User = userRepository.findById(command.id)  ?: throw IllegalArgumentException(UNKNOWN_USER)
		currentUser.remove()

		return userService.updateUser(currentUser)
	}
}
