package com.archeon.daysoff.infrastructure.repository.inMemory

import com.archeon.daysoff.business.domain.leave.LeaveRequest
import com.archeon.daysoff.business.port.repository.LeaveRepository
import com.archeon.daysoff.business.util.ErrorUtil

class InMemoryLeaveRepository : LeaveRepository {
	private val leaves: LinkedHashMap<String, LeaveRequest> = LinkedHashMap()

	override fun findOverlappingLeaveByUserId(leaveRequest: LeaveRequest): List<LeaveRequest> {
		return leaves.values.filter {
			it.id != leaveRequest.id
					&& it.userId == leaveRequest.userId
					&& (it.startDate in leaveRequest.startDate..leaveRequest.endDate
					|| it.endDate in leaveRequest.startDate..leaveRequest.endDate
					|| it.startDate <= leaveRequest.startDate && it.endDate >= leaveRequest.endDate // included in the period
					)
		}
	}

	override fun findAllByUserIdIn(userIds: List<String>): List<LeaveRequest> {
		return leaves.values.filter {
			userIds.contains(it.userId)
		}
	}

	override fun save(domain: LeaveRequest): LeaveRequest {
		leaves[domain.id.toString()] = domain
		return domain
	}

	override fun findAll(): List<LeaveRequest> {
		return leaves.values.filter { !it.isRemoved() }.toList()
	}

	override fun hardDeleteAll() {
		leaves.clear()
	}

	override fun findById(id: String): LeaveRequest? {
		return leaves[id]
	}
}
