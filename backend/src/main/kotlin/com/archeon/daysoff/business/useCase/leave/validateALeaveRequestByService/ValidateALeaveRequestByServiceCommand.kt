package com.archeon.daysoff.business.useCase.leave.validateALeaveRequestByService

import com.archeon.daysoff.business.useCase.Command

class ValidateALeaveRequestByServiceCommand(
	val leaveId: String,
	val comment: String?
) : Command
