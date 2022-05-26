package com.archeon.daysoff.infrastructure.gateway.authorization.inMemory.useCase

import com.archeon.daysoff.business.domain.leave.LeaveStatus
import com.archeon.daysoff.business.domain.user.Role
import com.archeon.daysoff.business.domain.user.User
import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.service.LeaveRequestService
import com.archeon.daysoff.business.useCase.UseCaseAuthorization
import com.archeon.daysoff.business.useCase.leave.setLeaveRequestAsDraft.SetLeaveRequestAsDraftCommand
import com.archeon.daysoff.business.util.ErrorUtil
import com.archeon.daysoff.business.util.ErrorUtil.ONLY_SUBMITTED_OR_REJECTED_CAN_BE_SET_BACK_AS_DRAFT
import com.archeon.daysoff.business.util.ErrorUtil.YOU_CAN_ONLY_SET_AS_DRAFT_LEAVE_REQUEST_IN_YOUR_TEAM
import com.archeon.daysoff.business.util.ErrorUtil.YOU_CAN_ONLY_SUBMIT_LEAVE_REQUEST_FOR_YOURSELF

class SetLeaveRequestAsDraftAuthorization(
	val leaveRequestService: LeaveRequestService,
	val userRepository: UserRepository
) : UseCaseAuthorization<SetLeaveRequestAsDraftCommand> {

	override fun doCheck(user: User, command: SetLeaveRequestAsDraftCommand) {
		val targetLeave = leaveRequestService.findById(command.leaveId)
		require(listOf(LeaveStatus.SUBMITTED, LeaveStatus.REJECTED).contains(targetLeave.getCurrentStatus()))
		{ ONLY_SUBMITTED_OR_REJECTED_CAN_BE_SET_BACK_AS_DRAFT }

		// if user has a simple USER ROLE, he can only submit his own leave request
		if (user.role == Role.USER && targetLeave.userId != user.id) {
			throw IllegalArgumentException(YOU_CAN_ONLY_SUBMIT_LEAVE_REQUEST_FOR_YOURSELF)
		}

		// if user has a SERVICE MANAGER ROLE, he can only submit leave request for people of service(s) that he manage
		if (user.role == Role.TEAM_MANAGER && targetLeave.userId != user.id) {
			val targetUser = userRepository.findById(targetLeave.userId) ?: throw IllegalArgumentException(ErrorUtil.UNKNOWN_USER)
			if (targetUser.teamIds.intersect(user.teamIds).isEmpty()) {
				throw IllegalArgumentException(YOU_CAN_ONLY_SET_AS_DRAFT_LEAVE_REQUEST_IN_YOUR_TEAM)
			}
		}
	}
}
