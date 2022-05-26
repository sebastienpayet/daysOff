package com.archeon.daysoff.business.useCase.leaveBalance.listLeaveBalances

import com.archeon.daysoff.business.domain.leave.LeaveBalance
import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.service.LeaveBalanceService
import com.archeon.daysoff.business.useCase.UseCase

class ListLeaveBalances(
	private val authorizationGateway: AuthorizationGateway,
	private val leaveBalanceService: LeaveBalanceService
) : UseCase<ListLeaveBalancesCommand, List<LeaveBalance>> {

	override fun handle(command: ListLeaveBalancesCommand): List<LeaveBalance> {
		authorizationGateway.checkAuthorization(this, command)
		return leaveBalanceService.loadReadableLeaves(command)
	}
}
