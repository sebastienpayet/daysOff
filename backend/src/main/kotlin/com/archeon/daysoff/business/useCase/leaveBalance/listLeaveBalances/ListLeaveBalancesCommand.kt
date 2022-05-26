package com.archeon.daysoff.business.useCase.leaveBalance.listLeaveBalances

import com.archeon.daysoff.business.useCase.Command

class ListLeaveBalancesCommand(
	val years: Set<Int> = emptySet()
) : Command
