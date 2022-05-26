package com.archeon.daysoff.business.useCase.leave.updateALeaveRequest

import com.archeon.daysoff.business.domain.leave.LeaveRequest
import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.service.LeaveRequestService
import com.archeon.daysoff.business.useCase.UseCase

class UpdateALeaveRequest(
	private val authorizationGateway: AuthorizationGateway,
	private val leaveRequestService: LeaveRequestService
) : UseCase<UpdateALeaveRequestCommand, LeaveRequest> {
	override fun handle(command: UpdateALeaveRequestCommand): LeaveRequest {
		authorizationGateway.checkAuthorization(this, command)

		leaveRequestService.findById(command.leaveId).let { leave ->
			val updateLeaveRequest = LeaveRequest(
				startDate = command.startDate,
				endDate = command.endDate,
				fullDayAtEnd = command.fullDayAtEnd,
				fullDayAtStart = command.fullDayAtStart,
				leaveType = command.leaveType,
				comment = command.comment ?: leave.comment,
				userId = leave.userId,
				depositDate = leave.depositDate,
				leaveWorkflows = leave.leaveWorkflows
			)

			return leaveRequestService.updateLeave(updateLeaveRequest.copyDomainData(leave))
		}
	}
}
