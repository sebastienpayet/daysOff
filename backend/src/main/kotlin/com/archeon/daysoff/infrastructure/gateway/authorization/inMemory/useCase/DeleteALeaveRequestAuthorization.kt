package com.archeon.daysoff.infrastructure.gateway.authorization.inMemory.useCase

import com.archeon.daysoff.business.domain.leave.LeaveStatus
import com.archeon.daysoff.business.domain.user.Role
import com.archeon.daysoff.business.domain.user.User
import com.archeon.daysoff.business.service.LeaveRequestService
import com.archeon.daysoff.business.useCase.UseCaseAuthorization
import com.archeon.daysoff.business.useCase.leave.deleteALeaveRequest.DeleteALeaveRequestCommand
import com.archeon.daysoff.business.util.ErrorUtil.YOU_CAN_ONLY_SET_AS_DRAFT_LEAVE_REQUEST_FOR_YOURSELF

class DeleteALeaveRequestAuthorization(val leaveRequestService: LeaveRequestService) :
    UseCaseAuthorization<DeleteALeaveRequestCommand> {
	override fun doCheck(user: User, command: DeleteALeaveRequestCommand) {
		val targetLeave = leaveRequestService.findById(command.leaveId)

		// if user has a simple USER ROLE, he can only delete his OWN DRAFT leave request
		if (listOf(Role.TEAM_MANAGER, Role.USER).contains(user.role)
			&& (targetLeave.userId != user.id || targetLeave.getCurrentStatus() != LeaveStatus.DRAFT)
		) {
			throw IllegalArgumentException(YOU_CAN_ONLY_SET_AS_DRAFT_LEAVE_REQUEST_FOR_YOURSELF)
		}
	}
}
