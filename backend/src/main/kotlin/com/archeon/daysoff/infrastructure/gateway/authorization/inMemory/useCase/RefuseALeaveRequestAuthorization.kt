package com.archeon.daysoff.infrastructure.gateway.authorization.inMemory.useCase

import com.archeon.daysoff.business.domain.user.Role
import com.archeon.daysoff.business.domain.user.User
import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.service.LeaveRequestService
import com.archeon.daysoff.business.useCase.UseCaseAuthorization
import com.archeon.daysoff.business.useCase.leave.refuseALeaveRequest.RefuseALeaveRequestCommand
import com.archeon.daysoff.business.util.ErrorUtil
import com.archeon.daysoff.business.util.ErrorUtil.YOU_CAN_ONLY_REFUSE_LEAVE_REQUEST_IN_YOUR_TEAM

class RefuseALeaveRequestAuthorization(
	val leaveRequestService: LeaveRequestService,
	val userRepository: UserRepository
) : UseCaseAuthorization<RefuseALeaveRequestCommand> {

	override fun doCheck(user: User, command: RefuseALeaveRequestCommand) {
		val targetLeave = leaveRequestService.findById(command.leaveId)

		// if user has a SERVICE MANAGER ROLE, he can only submit leave request for people of service(s) that he manage
		if (user.role == Role.TEAM_MANAGER && targetLeave.userId != user.id) {
			val targetUser = userRepository.findById(targetLeave.userId) ?: throw IllegalArgumentException(ErrorUtil.UNKNOWN_USER)
			if (targetUser.teamIds.intersect(user.teamIds).isEmpty()) {
				throw IllegalArgumentException(YOU_CAN_ONLY_REFUSE_LEAVE_REQUEST_IN_YOUR_TEAM)
			}
		}

	}
}
