package com.archeon.daysoff.infrastructure.gateway.authorization.inMemory.useCase

import com.archeon.daysoff.business.domain.leave.LeaveStatus
import com.archeon.daysoff.business.domain.user.Role
import com.archeon.daysoff.business.domain.user.User
import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.service.LeaveRequestService
import com.archeon.daysoff.business.useCase.UseCaseAuthorization
import com.archeon.daysoff.business.useCase.leave.updateALeaveRequest.UpdateALeaveRequestCommand
import com.archeon.daysoff.business.util.ErrorUtil
import com.archeon.daysoff.business.util.ErrorUtil.YOU_CAN_ONLY_UPDATE_DRAFT_LEAVE_REQUEST
import com.archeon.daysoff.business.util.ErrorUtil.YOU_CAN_ONLY_UPDATE_LEAVE_IN_YOUR_TEAM
import com.archeon.daysoff.business.util.ErrorUtil.YOU_CAN_ONLY_UPDATE_LEAVE_REQUEST_FOR_YOURSELF

class UpdateALeaveRequestAuthorization(
	val leaveRequestService: LeaveRequestService,
	val userRepository: UserRepository
) : UseCaseAuthorization<UpdateALeaveRequestCommand> {

	override fun doCheck(user: User, command: UpdateALeaveRequestCommand) {
		val targetLeave = leaveRequestService.findById(command.leaveId)
		if (targetLeave.getCurrentStatus() != LeaveStatus.DRAFT) {
			throw IllegalArgumentException(YOU_CAN_ONLY_UPDATE_DRAFT_LEAVE_REQUEST)
		}

		// if user has a SERVICE MANAGER ROLE, he can only update leave request for people of service(s) that he manage
		if (user.role == Role.TEAM_MANAGER && targetLeave.userId != user.id) {
			val targetUser =
				userRepository.findById(targetLeave.userId) ?: throw IllegalArgumentException(ErrorUtil.UNKNOWN_USER)
			if (targetUser.teamIds.intersect(user.teamIds).isEmpty()) {
				throw IllegalArgumentException(YOU_CAN_ONLY_UPDATE_LEAVE_IN_YOUR_TEAM)
			}
		} else if (user.role != Role.ADMINISTRATOR && targetLeave.userId != user.id) {
			throw IllegalArgumentException(YOU_CAN_ONLY_UPDATE_LEAVE_REQUEST_FOR_YOURSELF)
		}
	}
}
