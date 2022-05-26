package com.archeon.daysoff.business.useCase.leave.submitALeaveRequest

import com.archeon.daysoff.business.domain.leave.LeaveRequest
import com.archeon.daysoff.business.domain.leave.LeaveStatus
import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.service.LeaveRequestService
import com.archeon.daysoff.business.useCase.UseCase

class SubmitALeaveRequest(
	private val authorizationGateway: AuthorizationGateway,
	private val leaveService: LeaveRequestService
) : UseCase<SubmitALeaveRequestCommand, LeaveRequest> {
	override fun handle(command: SubmitALeaveRequestCommand): LeaveRequest {
		authorizationGateway.checkAuthorization(this, command)
		return leaveService.updateWorkflow(command.leaveId, LeaveStatus.SUBMITTED, command.comment ?: "")
	}
}
