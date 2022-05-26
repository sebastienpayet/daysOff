package com.archeon.daysoff.business.useCase.leave.validateALeaveRequestByManagement

import com.archeon.daysoff.business.domain.leave.BalanceType
import com.archeon.daysoff.business.domain.leave.LeaveRequest
import com.archeon.daysoff.business.domain.leave.LeaveStatus
import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.service.LeaveRequestService
import com.archeon.daysoff.business.useCase.UseCase
import com.archeon.daysoff.business.useCase.leaveBalance.createALeaveBalance.CreateALeaveBalance
import com.archeon.daysoff.business.useCase.leaveBalance.createALeaveBalance.CreateALeaveBalanceCommand
import com.archeon.daysoff.business.util.ErrorUtil.UNKNOWN_LEAVE
import java.math.BigDecimal
import java.math.RoundingMode

class ValidateALeaveRequestByManagement(
	private val authorizationGateway: AuthorizationGateway,
	private val leaveRequestService: LeaveRequestService,
	private val createALeaveBalance: CreateALeaveBalance
) : UseCase<ValidateALeaveRequestByManagementCommand, LeaveRequest> {
	override fun handle(command: ValidateALeaveRequestByManagementCommand): LeaveRequest {
		authorizationGateway.checkAuthorization(this, command)
		val leave = leaveRequestService.findById(leaveId = command.leaveId)

		val commandTotal = (command.special
			.plus(command.annual)
			.plus(command.recovery)
			.plus(command.timeSavingAccount)
			.plus(command.workingTimeReduction))
			.setScale(2, RoundingMode.HALF_EVEN)

		// check command consistency
		require(leaveRequestService.computeLeaveDuration(leave).setScale(2, RoundingMode.HALF_EVEN) == commandTotal)
		{ "leave balances total must be equal to leave duration" }

		// create leave balances
		if (command.special > BigDecimal.ZERO) {
			createLeaveBalance("SPECIAL", leave, command.special)
		}
		if (command.annual > BigDecimal.ZERO) {
			createLeaveBalance("ANNUAL", leave, command.annual)
		}
		if (command.recovery > BigDecimal.ZERO) {
			createLeaveBalance("RECOVERY", leave, command.recovery)
		}
		if (command.timeSavingAccount > BigDecimal.ZERO) {
			createLeaveBalance("TIME_SAVING_ACCOUNT", leave, command.timeSavingAccount)
		}
		if (command.workingTimeReduction > BigDecimal.ZERO) {
			createLeaveBalance("TIME_SAVING_ACCOUNT", leave, command.workingTimeReduction)
		}

		return leaveRequestService.updateWorkflow(
			command.leaveId,
			LeaveStatus.MANAGEMENT_APPROVED,
			command.comment ?: ""
		)
	}

	private fun createLeaveBalance(type: String, leaveRequest: LeaveRequest, amount: BigDecimal) {
		createALeaveBalance.handle(
			CreateALeaveBalanceCommand(
				leaveId = leaveRequest.id,
				userId = leaveRequest.userId,
				leaveType = type,
				balanceType = BalanceType.DEBIT.name,
				year = leaveRequest.startDate.year,
				amount = amount,
				comment = ""
			)
		)
	}
}
