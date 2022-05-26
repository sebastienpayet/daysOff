package com.archeon.daysoff.business.useCase.leave.deleteALeaveRequest

import com.archeon.daysoff.business.domain.leave.LeaveRequest
import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.service.LeaveRequestService
import com.archeon.daysoff.business.useCase.UseCase

class DeleteALeaveRequest(
	private val authorizationGateway: AuthorizationGateway,
	private val leaveRequestService: LeaveRequestService
) : UseCase<DeleteALeaveRequestCommand, LeaveRequest> {
	override fun handle(command: DeleteALeaveRequestCommand): LeaveRequest {
		authorizationGateway.checkAuthorization(this, command)
		return leaveRequestService.deleteLeave(command.leaveId)
	}
}
