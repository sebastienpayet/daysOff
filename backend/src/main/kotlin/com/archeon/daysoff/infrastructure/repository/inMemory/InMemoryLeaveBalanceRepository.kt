package com.archeon.daysoff.infrastructure.repository.inMemory

import com.archeon.daysoff.business.domain.leave.LeaveBalance
import com.archeon.daysoff.business.port.repository.LeaveBalanceRepository

class InMemoryLeaveBalanceRepository : LeaveBalanceRepository {

	private val leaveBalances: LinkedHashMap<String, LeaveBalance> = LinkedHashMap()

	override fun findByUserId(userId: String): List<LeaveBalance> {
		return leaveBalances.values.filter { it.userId == userId }
	}

	override fun findByYearIn(years: Set<Int>): List<LeaveBalance> {
		return leaveBalances.values.filter { years.contains(it.year) }
	}

	override fun findByUserIdAndYearIn(userId: String, years: Set<Int>): List<LeaveBalance> {
		return leaveBalances.values.filter { it.userId == userId && years.contains(it.year) }
	}

	override fun save(domain: LeaveBalance): LeaveBalance {
		leaveBalances[domain.id.toString()] = domain
		return domain
	}

	override fun findAll(): List<LeaveBalance> {
		return leaveBalances.values.toList()
	}

	override fun hardDeleteAll() {
		leaveBalances.clear()
	}

	override fun findById(id: String): LeaveBalance? {
		return leaveBalances[id]
	}

}
