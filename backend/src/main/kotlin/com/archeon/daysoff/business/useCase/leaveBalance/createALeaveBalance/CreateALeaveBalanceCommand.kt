package com.archeon.daysoff.business.useCase.leaveBalance.createALeaveBalance

import com.archeon.daysoff.business.useCase.Command
import java.math.BigDecimal

class CreateALeaveBalanceCommand(
	val leaveId: String?,
	val userId: String,
	val leaveType: String,
	val balanceType: String,
	val year: Int,
	val amount: BigDecimal,
	val comment: String?
) : Command
