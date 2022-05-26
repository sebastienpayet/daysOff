package com.archeon.daysoff.business.useCase.leave.updateALeaveRequest

import com.archeon.daysoff.business.domain.leave.LeaveType
import com.archeon.daysoff.business.useCase.Command
import java.time.LocalDate

class UpdateALeaveRequestCommand(
	val leaveId: String,
	val startDate: LocalDate,
	val fullDayAtStart: Boolean = true,
	val endDate: LocalDate,
	val fullDayAtEnd: Boolean = true,
	val leaveType: LeaveType,
	val comment : String?
) : Command
