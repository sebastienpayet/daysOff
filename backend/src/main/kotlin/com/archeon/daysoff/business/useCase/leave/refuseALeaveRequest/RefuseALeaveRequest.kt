package com.archeon.daysoff.business.useCase.leave.refuseALeaveRequest

import com.archeon.daysoff.business.domain.leave.LeaveRequest
import com.archeon.daysoff.business.domain.leave.LeaveStatus
import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.service.LeaveRequestService
import com.archeon.daysoff.business.useCase.UseCase

class RefuseALeaveRequest(
	private val authorizationGateway: AuthorizationGateway,
	private val leaveRequestService: LeaveRequestService
) : UseCase<RefuseALeaveRequestCommand, LeaveRequest> {
	override fun handle(command: RefuseALeaveRequestCommand): LeaveRequest {
		authorizationGateway.checkAuthorization(this, command)
		return leaveRequestService.updateWorkflow(command.leaveId, LeaveStatus.REJECTED, command.comment ?: "")
	}
}
