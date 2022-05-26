package com.archeon.daysoff.business.useCase.leaveBalance.createALeaveBalance

import com.archeon.daysoff.business.domain.leave.BalanceType
import com.archeon.daysoff.business.domain.leave.BalanceType.CREDIT
import com.archeon.daysoff.business.domain.leave.LeaveBalance
import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.service.LeaveBalanceService
import com.archeon.daysoff.business.useCase.UseCase
import java.math.BigDecimal

class CreateALeaveBalance(
	private val authorizationGateway: AuthorizationGateway,
	private val leaveBalanceService: LeaveBalanceService
) : UseCase<CreateALeaveBalanceCommand, LeaveBalance> {

	override fun handle(command: CreateALeaveBalanceCommand): LeaveBalance {
		authorizationGateway.checkAuthorization(this, command)

		// check for command consistency
		val balanceType = BalanceType.valueOf(command.balanceType.trim())
		if (balanceType == CREDIT && command.leaveId != null) {
			throw IllegalArgumentException("leaveId must be null for CREDIT balance")
		}
		require(command.amount > BigDecimal.ZERO) { "AmountMustBePositive" }
		
		return leaveBalanceService.createLeaveBalance(command)
	}
}
