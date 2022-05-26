package com.archeon.daysoff.business.port.repository

import com.archeon.daysoff.business.domain.leave.LeaveBalance

interface LeaveBalanceRepository : Repository<LeaveBalance> {
	fun findByUserId(userId: String): List<LeaveBalance>
	fun findByYearIn(years: Set<Int>): List<LeaveBalance>
	fun findByUserIdAndYearIn(userId: String, years: Set<Int>): List<LeaveBalance>
}
