package com.archeon.daysoff.business.domain.leave

import com.archeon.daysoff.business.domain.DomainModel
import java.math.BigDecimal
import java.time.DayOfWeek.SATURDAY
import java.time.DayOfWeek.SUNDAY
import java.time.Instant
import java.time.LocalDate

class LeaveRequest(
	val depositDate: Instant,
	val startDate: LocalDate,
	val fullDayAtStart: Boolean,
	val endDate: LocalDate,
	val fullDayAtEnd: Boolean,
	val leaveWorkflows: Set<LeaveWorkflow>,
	val leaveType: LeaveType,
	val userId: String,
	val comment: String = "",
	id: String? = null,
	version: Long = 0,
	removed: Boolean = false,
	createdBy: String? = null,
	createdDate: Instant? = null,
	lastModifiedBy: String? = null,
	lastModifiedDate: Instant? = null
) : DomainModel<LeaveRequest>(
	id = id,
	createdDate = createdDate,
	createdBy = createdBy,
	removed = removed,
	lastModifiedBy = lastModifiedBy,
	lastModifiedDate = lastModifiedDate,
	version = version
) {
	override fun copyDomainData(source: LeaveRequest): LeaveRequest {
		return LeaveRequest(
			depositDate = depositDate,
			startDate = startDate,
			fullDayAtStart = fullDayAtStart,
			endDate = endDate,
			fullDayAtEnd = fullDayAtEnd,
			leaveWorkflows = leaveWorkflows,
			leaveType = leaveType,
			userId = userId,
			comment = comment,
			id = source.id,
			version = source.version,
			removed = source.isRemoved(),
			createdBy = source.createdBy,
			createdDate = source.createdDate,
			lastModifiedBy = source.lastModifiedBy,
			lastModifiedDate = source.lastModifiedDate
		)
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as LeaveRequest

		if (startDate != other.startDate) return false
		if (endDate != other.endDate) return false
		if (leaveType != other.leaveType) return false
		if (userId != other.userId) return false

		return true
	}

	override fun hashCode(): Int {
		var result = startDate.hashCode()
		result = 31 * result + endDate.hashCode()
		result = 31 * result + leaveType.hashCode()
		result = 31 * result + userId.hashCode()
		return result
	}

	fun getCurrentStatus(): LeaveStatus {
		return leaveWorkflows.maxByOrNull { it.date }?.leaveStatus ?: LeaveStatus.DRAFT
	}
}
