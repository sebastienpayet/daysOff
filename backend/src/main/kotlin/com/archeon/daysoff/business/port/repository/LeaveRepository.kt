package com.archeon.daysoff.business.port.repository

import com.archeon.daysoff.business.domain.leave.LeaveRequest

interface LeaveRepository : Repository<LeaveRequest> {
	fun findOverlappingLeaveByUserId(leaveRequest: LeaveRequest): List<LeaveRequest>
	fun findAllByUserIdIn(userIds: List<String>): List<LeaveRequest>
}
