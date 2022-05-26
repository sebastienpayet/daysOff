package com.archeon.daysoff.business.useCase.leave.validateALeaveRequestByManagement

import com.archeon.daysoff.business.useCase.Command
import java.math.BigDecimal

class ValidateALeaveRequestByManagementCommand(
	val leaveId: String,
	val comment: String?,
	val annual: BigDecimal,
	val special: BigDecimal,
	val recovery: BigDecimal,
	val timeSavingAccount: BigDecimal,
	val workingTimeReduction: BigDecimal
) : Command
