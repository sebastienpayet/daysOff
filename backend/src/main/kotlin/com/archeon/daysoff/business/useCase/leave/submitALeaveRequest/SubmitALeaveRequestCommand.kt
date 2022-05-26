package com.archeon.daysoff.business.useCase.leave.submitALeaveRequest

import com.archeon.daysoff.business.useCase.Command

class SubmitALeaveRequestCommand (
	val leaveId: String,
	val comment: String?
) : Command
