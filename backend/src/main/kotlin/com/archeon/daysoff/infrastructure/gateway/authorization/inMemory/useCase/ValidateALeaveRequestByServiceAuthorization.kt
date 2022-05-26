package com.archeon.daysoff.infrastructure.gateway.authorization.inMemory.useCase

import com.archeon.daysoff.business.domain.user.Role
import com.archeon.daysoff.business.domain.user.User
import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.service.LeaveRequestService
import com.archeon.daysoff.business.useCase.UseCaseAuthorization
import com.archeon.daysoff.business.useCase.leave.validateALeaveRequestByService.ValidateALeaveRequestByServiceCommand
import com.archeon.daysoff.business.util.ErrorUtil
import com.archeon.daysoff.business.util.ErrorUtil.ACCESS_FORBIDDEN
import com.archeon.daysoff.business.util.ErrorUtil.YOU_CAN_ONLY_VALIDATE_LEAVE_REQUEST_IN_YOUR_TEAM

class ValidateALeaveRequestByServiceAuthorization(
	val leaveRequestService: LeaveRequestService,
	val userRepository: UserRepository
) : UseCaseAuthorization<ValidateALeaveRequestByServiceCommand> {

	override fun doCheck(user: User, command: ValidateALeaveRequestByServiceCommand) {
		val targetLeave = leaveRequestService.findById(command.leaveId)
		when (user.role) {
			Role.USER -> {
				throw IllegalArgumentException(ACCESS_FORBIDDEN)
			}
			Role.TEAM_MANAGER -> {
				val targetUser = userRepository.findById(targetLeave.userId) ?: throw IllegalArgumentException(ErrorUtil.UNKNOWN_USER)
				if (targetUser.teamIds.intersect(user.teamIds).isEmpty()) {
					throw IllegalArgumentException(YOU_CAN_ONLY_VALIDATE_LEAVE_REQUEST_IN_YOUR_TEAM)
				}
			}
			else -> {
			}// Nothing to do
		}

		// check if an existing overlapping leaveRequest already exists
		leaveRequestService.checkIsNoOtherRequestInPeriod(targetLeave)
	}
}
