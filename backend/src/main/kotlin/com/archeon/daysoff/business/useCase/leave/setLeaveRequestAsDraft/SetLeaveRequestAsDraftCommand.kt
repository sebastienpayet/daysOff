package com.archeon.daysoff.business.useCase.leave.setLeaveRequestAsDraft

import com.archeon.daysoff.business.useCase.Command

class SetLeaveRequestAsDraftCommand (
	val leaveId: String,
	val comment: String?
) : Command
