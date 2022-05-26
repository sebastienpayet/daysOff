package com.archeon.daysoff.business.useCase.leave.setLeaveRequestAsDraft

import com.archeon.daysoff.business.domain.leave.LeaveRequest
import com.archeon.daysoff.business.domain.leave.LeaveStatus
import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.service.LeaveRequestService
import com.archeon.daysoff.business.useCase.UseCase

class SetLeaveRequestAsDraft(
	private val authorizationGateway: AuthorizationGateway,
	private val leaveRequestService: LeaveRequestService
) : UseCase<SetLeaveRequestAsDraftCommand, LeaveRequest> {
	override fun handle(command: SetLeaveRequestAsDraftCommand): LeaveRequest {
		authorizationGateway.checkAuthorization(this, command)
		return leaveRequestService.updateWorkflow(command.leaveId, LeaveStatus.DRAFT, command.comment ?: "")
	}
}
