package com.archeon.daysoff.business.useCase.leave.createALeaveRequest

import com.archeon.daysoff.business.domain.leave.LeaveType
import com.archeon.daysoff.business.useCase.Command
import java.time.LocalDate

class CreateALeaveRequestCommand(
	val startDate: LocalDate,
	val fullDayAtStart: Boolean = true,
	val endDate: LocalDate,
	val fullDayAtEnd: Boolean = true,
	val leaveType: LeaveType,
	val userId: String,
	val comment : String?
) : Command
