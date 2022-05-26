package com.archeon.daysoff.infrastructure.gateway.authorization.inMemory.useCase

import com.archeon.daysoff.business.domain.user.User
import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.service.LeaveRequestService
import com.archeon.daysoff.business.useCase.UseCaseAuthorization
import com.archeon.daysoff.business.useCase.leave.validateALeaveRequestByManagement.ValidateALeaveRequestByManagementCommand
import com.archeon.daysoff.business.util.ErrorUtil.UNKNOWN_LEAVE

class ValidateALeaveRequestByManagementAuthorization(
	val leaveRequestService: LeaveRequestService,
	val userRepository: UserRepository
) : UseCaseAuthorization<ValidateALeaveRequestByManagementCommand> {

	override fun doCheck(user: User, command: ValidateALeaveRequestByManagementCommand) {
		val targetLeave = leaveRequestService.findById(command.leaveId)

		// check if an existing overlapping leaveRequest already exists
		leaveRequestService.checkIsNoOtherRequestInPeriod(targetLeave)
	}
}
