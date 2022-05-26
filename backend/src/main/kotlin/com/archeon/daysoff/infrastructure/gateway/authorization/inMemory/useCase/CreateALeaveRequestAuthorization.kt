package com.archeon.daysoff.infrastructure.gateway.authorization.inMemory.useCase

import com.archeon.daysoff.business.domain.user.Role
import com.archeon.daysoff.business.domain.user.User
import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.useCase.leave.createALeaveRequest.CreateALeaveRequestCommand
import com.archeon.daysoff.business.useCase.UseCaseAuthorization
import com.archeon.daysoff.business.util.ErrorUtil.UNKNOWN_USER
import com.archeon.daysoff.business.util.ErrorUtil.YOU_CAN_ONLY_CREATE_LEAVE_REQUEST_FOR_YOURSELF
import com.archeon.daysoff.business.util.ErrorUtil.YOU_CAN_ONLY_CREATE_LEAVE_REQUEST_IN_YOUR_TEAM

class CreateALeaveRequestAuthorization(
	val userRepository: UserRepository
) : UseCaseAuthorization<CreateALeaveRequestCommand> {

	override fun doCheck(user: User, command: CreateALeaveRequestCommand) {
		// if user has a simple USER ROLE, he can only create a leave request for himself
		if (user.role == Role.USER && command.userId != user.id) {
			throw IllegalArgumentException(YOU_CAN_ONLY_CREATE_LEAVE_REQUEST_FOR_YOURSELF)
		}

		// if user has a SERVICE MANAGER ROLE, he can only submit leave request for people of service(s) that he manage
		if (user.role == Role.TEAM_MANAGER && command.userId != user.id) {
			val targetUser = userRepository.findById(command.userId) ?: throw IllegalArgumentException(UNKNOWN_USER)
			if (targetUser.teamIds.intersect(user.teamIds).isEmpty()) {
				throw IllegalArgumentException(YOU_CAN_ONLY_CREATE_LEAVE_REQUEST_IN_YOUR_TEAM)
			}
		}
	}
}
