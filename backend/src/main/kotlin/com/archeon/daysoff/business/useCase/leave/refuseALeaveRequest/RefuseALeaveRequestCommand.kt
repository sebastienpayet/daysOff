package com.archeon.daysoff.business.useCase.leave.refuseALeaveRequest

import com.archeon.daysoff.business.useCase.Command

class RefuseALeaveRequestCommand (
	val leaveId: String,
	val comment: String?
) : Command
