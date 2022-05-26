package com.archeon.daysoff.infrastructure.controller.leaveBalance

import com.archeon.daysoff.business.useCase.leaveBalance.createALeaveBalance.CreateALeaveBalanceCommand
import com.archeon.daysoff.business.useCase.leaveBalance.listLeaveBalances.ListLeaveBalancesCommand
import com.archeon.daysoff.infrastructure.resource.output.leaveBalance.LeaveBalanceOutput
import org.springframework.web.bind.annotation.RequestBody

interface LeaveBalanceApi {
	fun createALeaveBalance(createALeaveBalanceCommand: CreateALeaveBalanceCommand): LeaveBalanceOutput
	fun listLeaveBalances(listLeaveBalancesCommand: ListLeaveBalancesCommand): List<LeaveBalanceOutput>
}
