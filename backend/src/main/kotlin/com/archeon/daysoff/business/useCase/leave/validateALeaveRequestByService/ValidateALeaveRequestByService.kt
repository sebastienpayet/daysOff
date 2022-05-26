package com.archeon.daysoff.business.useCase.leave.validateALeaveRequestByService

import com.archeon.daysoff.business.domain.leave.LeaveRequest
import com.archeon.daysoff.business.domain.leave.LeaveStatus
import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.service.LeaveRequestService
import com.archeon.daysoff.business.useCase.UseCase

class ValidateALeaveRequestByService(
	private val authorizationGateway: AuthorizationGateway,
	private val leaveRequestService: LeaveRequestService
) : UseCase<ValidateALeaveRequestByServiceCommand, LeaveRequest> {
	override fun handle(command: ValidateALeaveRequestByServiceCommand): LeaveRequest {
		authorizationGateway.checkAuthorization(this, command)
		return leaveRequestService.updateWorkflow(command.leaveId, LeaveStatus.SERVICE_APPROVED, command.comment ?: "")
	}
}
