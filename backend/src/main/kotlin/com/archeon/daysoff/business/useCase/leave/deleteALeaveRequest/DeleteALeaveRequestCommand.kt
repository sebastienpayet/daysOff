package com.archeon.daysoff.business.useCase.leave.deleteALeaveRequest

import com.archeon.daysoff.business.useCase.Command

class DeleteALeaveRequestCommand (
	val leaveId: String,
	val comment: String = ""
) : Command
