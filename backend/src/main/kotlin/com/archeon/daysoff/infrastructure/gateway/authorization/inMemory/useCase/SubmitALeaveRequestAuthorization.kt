package com.archeon.daysoff.infrastructure.gateway.authorization.inMemory.useCase

import com.archeon.daysoff.business.domain.leave.LeaveStatus
import com.archeon.daysoff.business.domain.user.Role
import com.archeon.daysoff.business.domain.user.User
import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.service.LeaveRequestService
import com.archeon.daysoff.business.useCase.UseCaseAuthorization
import com.archeon.daysoff.business.useCase.leave.submitALeaveRequest.SubmitALeaveRequestCommand
import com.archeon.daysoff.business.util.ErrorUtil.ONLY_DRAFT_CAN_BE_SUBMITTED
import com.archeon.daysoff.business.util.ErrorUtil.UNKNOWN_USER
import com.archeon.daysoff.business.util.ErrorUtil.YOU_CAN_ONLY_SUBMIT_LEAVE_IN_YOUR_TEAM
import com.archeon.daysoff.business.util.ErrorUtil.YOU_CAN_ONLY_SUBMIT_LEAVE_REQUEST_FOR_YOURSELF

class SubmitALeaveRequestAuthorization(
	val leaveRequestService: LeaveRequestService,
	val userRepository: UserRepository
) : UseCaseAuthorization<SubmitALeaveRequestCommand> {

	override fun doCheck(user: User, command: SubmitALeaveRequestCommand) {
		val targetLeave = leaveRequestService.findById(command.leaveId)

		// if user has a simple USER ROLE, he can only submit his own leave request
		if (user.role == Role.USER && targetLeave.userId != user.id) {
			throw IllegalArgumentException(YOU_CAN_ONLY_SUBMIT_LEAVE_REQUEST_FOR_YOURSELF)
		}

		// if user has a SERVICE MANAGER ROLE, he can only submit leave request for people of service(s) that he manage
		if (user.role == Role.TEAM_MANAGER && targetLeave.userId != user.id) {
			val targetUser = userRepository.findById(targetLeave.userId) ?: throw IllegalArgumentException(UNKNOWN_USER)
			if (targetUser.teamIds.intersect(user.teamIds).isEmpty()) {
				throw IllegalArgumentException(YOU_CAN_ONLY_SUBMIT_LEAVE_IN_YOUR_TEAM)
			}
		}

		require(targetLeave.getCurrentStatus() == LeaveStatus.DRAFT) { ONLY_DRAFT_CAN_BE_SUBMITTED }
		// check if an existing overlapping leaveRequest already exists
		leaveRequestService.checkIsNoOtherRequestInPeriod(targetLeave)
	}
}
