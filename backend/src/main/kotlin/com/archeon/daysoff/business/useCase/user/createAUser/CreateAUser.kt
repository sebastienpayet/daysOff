package com.archeon.daysoff.business.useCase.user.createAUser

import com.archeon.daysoff.business.domain.user.User
import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.port.service.HashService
import com.archeon.daysoff.business.service.UserService
import com.archeon.daysoff.business.useCase.UseCase
import com.archeon.daysoff.business.util.ErrorUtil.USER_ALREADY_EXISTS

class CreateAUser(
	private val userRepository: UserRepository,
	private val authorizationGateway: AuthorizationGateway,
	private val hashService: HashService,
	private val userService: UserService
) : UseCase<CreateUserCommand, User> {

	override fun handle(command: CreateUserCommand): User {
		authorizationGateway.checkAuthorization(this, command)

		// check that user doesn't already exists
		val checkedUser = userRepository.findByEmail(email = command.email)
		require(checkedUser == null || checkedUser.isRemoved()) { USER_ALREADY_EXISTS }

		return userService.updateUser(
			User(
				firstname = command.firstname,
				lastname = command.lastname,
				hasMinorChild = command.hasMinorChild,
				teamIds = command.teamIds,
				role = command.role,
				email = command.email.trim().lowercase(),
				credential = hashService.hash(command.credential)
			)
		)
	}
}
