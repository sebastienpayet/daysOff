package com.archeon.daysoff.business.useCase.leave.listLeaves

import com.archeon.daysoff.business.domain.leave.LeaveRequest
import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.service.LeaveRequestService
import com.archeon.daysoff.business.useCase.UseCase

class ListLeaveRequests(
	private val authorizationGateway: AuthorizationGateway,
	private val leaveRequestService: LeaveRequestService
) : UseCase<ListLeaveRequestsCommand, List<LeaveRequest>> {
	override fun handle(command: ListLeaveRequestsCommand): List<LeaveRequest> {
		authorizationGateway.checkAuthorization(this, command)
		return leaveRequestService.loadReadableLeaves()
	}
}
