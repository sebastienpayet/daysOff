package com.archeon.daysoff.business.useCase.user.updateAUser

import com.archeon.daysoff.business.domain.user.User
import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.port.service.HashService
import com.archeon.daysoff.business.service.UserService
import com.archeon.daysoff.business.useCase.UseCase
import com.archeon.daysoff.business.util.ErrorUtil
import com.archeon.daysoff.business.util.ErrorUtil.UNKNOWN_USER
import com.archeon.daysoff.business.util.ErrorUtil.USER_EMAIL_ALREADY_USED

class UpdateAUser(
	private val userRepository: UserRepository,
	private val authorizationGateway: AuthorizationGateway,
	private val hashService: HashService,
	private val userService: UserService
) : UseCase<UpdateUserCommand, User> {

	override fun handle(command: UpdateUserCommand): User {
		authorizationGateway.checkAuthorization(this, command)

		// check that user exists
		val currentUser: User = userRepository.findById(command.id) ?: throw IllegalArgumentException(UNKNOWN_USER)
		val checkUser = userRepository.findByEmail(command.email)

		require(checkUser == null || checkUser.id == currentUser.id  ) { USER_EMAIL_ALREADY_USED }

		var newUser = User(
			firstname = command.firstname,
			lastname = command.lastname,
			hasMinorChild = command.hasMinorChild,
			teamIds = command.teamIds,
			email = command.email.trim().lowercase(),
			role = currentUser.role,
			credential = if (command.credential != null) {
				hashService.hash(command.credential)
			} else currentUser.credential
		)

		newUser = newUser.copyDomainData(currentUser)
		return userService.updateUser(newUser)
	}
}
