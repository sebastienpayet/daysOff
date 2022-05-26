package com.archeon.daysoff.business.domain.leave

import com.archeon.daysoff.business.domain.DomainModel
import java.math.BigDecimal
import java.time.Instant

class LeaveBalance(
	val leaveType: LeaveType,
	val balanceType: BalanceType,
	val amount: BigDecimal,
	val year: Int,
	val userId: String,
	val comment: String = "",
	val leaveId: String? = "", // have to be empty for CREDIT, must not be empty to DEBIT
	id: String? = null,
	version: Long = 0,
	removed: Boolean = false,
	createdBy: String? = null,
	createdDate: Instant? = null,
	lastModifiedBy: String? = null,
	lastModifiedDate: Instant? = null
) : DomainModel<LeaveBalance>(
	id = id,
	createdDate = createdDate,
	createdBy = createdBy,
	removed = removed,
	lastModifiedBy = lastModifiedBy,
	lastModifiedDate = lastModifiedDate,
	version = version
) {
	override fun copyDomainData(source: LeaveBalance): LeaveBalance {
		return LeaveBalance(
			leaveType = leaveType,
			balanceType = balanceType,
			year = year,
			amount = amount,
			userId = userId,
			comment = comment,
			leaveId = leaveId,
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

		other as LeaveBalance

		if (leaveType != other.leaveType) return false
		if (year != other.year) return false
		if (userId != other.userId) return false
		if (leaveId != other.leaveId) return false

		return true
	}

	override fun hashCode(): Int {
		var result = leaveType.hashCode()
		result = 31 * result + year
		result = 31 * result + leaveId.hashCode()
		result = 31 * result + userId.hashCode()
		return result
	}
}
