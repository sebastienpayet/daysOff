package com.archeon.daysoff.business.useCase.leave.createALeaveRequest

import com.archeon.daysoff.business.domain.leave.LeaveRequest
import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.service.LeaveRequestService
import com.archeon.daysoff.business.useCase.UseCase

class CreateALeaveRequest(
	private val authorizationGateway: AuthorizationGateway,
	private val leaveRequestService: LeaveRequestService
) : UseCase<CreateALeaveRequestCommand, LeaveRequest> {
	override fun handle(command: CreateALeaveRequestCommand): LeaveRequest {
		authorizationGateway.checkAuthorization(this, command)
		return leaveRequestService.createLeave(command)
	}
}
