package com.archeon.daysoff.infrastructure.controller.leaveBalance

import com.archeon.daysoff.business.useCase.leaveBalance.createALeaveBalance.CreateALeaveBalance
import com.archeon.daysoff.business.useCase.leaveBalance.createALeaveBalance.CreateALeaveBalanceCommand
import com.archeon.daysoff.business.useCase.leaveBalance.listLeaveBalances.ListLeaveBalances
import com.archeon.daysoff.business.useCase.leaveBalance.listLeaveBalances.ListLeaveBalancesCommand
import com.archeon.daysoff.infrastructure.converter.output.leaveBalance.LeaveBalanceOutputConverter
import com.archeon.daysoff.infrastructure.resource.output.leaveBalance.LeaveBalanceOutput
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("leaveBalance")
class LeaveBalanceController(
	private val createALeaveBalance: CreateALeaveBalance,
	private val listLeaveBalances: ListLeaveBalances,
	private val leaveBalanceOutputConverter: LeaveBalanceOutputConverter
) : LeaveBalanceApi {

	@PostMapping("listLeaveBalances")
	override fun listLeaveBalances(@RequestBody listLeaveBalancesCommand: ListLeaveBalancesCommand): List<LeaveBalanceOutput> {
		return listLeaveBalances.handle(listLeaveBalancesCommand).map { leaveBalanceOutputConverter.toResource(it) }
			.toList()
	}

	@PostMapping
	override fun createALeaveBalance(@RequestBody createALeaveBalanceCommand: CreateALeaveBalanceCommand): LeaveBalanceOutput {
		return leaveBalanceOutputConverter.toResource(createALeaveBalance.handle(createALeaveBalanceCommand))
	}
}
