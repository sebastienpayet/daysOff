package com.archeon.daysoff.infrastructure.converter.mongoDb.leaveBalance

import com.archeon.daysoff.business.domain.leave.LeaveBalance
import com.archeon.daysoff.infrastructure.converter.AbstractConverter
import com.archeon.daysoff.infrastructure.resource.mongoDb.leaveBalance.LeaveBalanceDocument
import org.springframework.stereotype.Component

@Component
class LeaveBalanceDocumentConverter : AbstractConverter<LeaveBalance, LeaveBalanceDocument>() {
	override fun toResource(domain: LeaveBalance): LeaveBalanceDocument {
		return LeaveBalanceDocument(
			leaveType = domain.leaveType,
			balanceType = domain.balanceType,
			amount = domain.amount,
			year = domain.year,
			userId = domain.userId,
			comment = domain.comment,
			leaveId = domain.leaveId,
			id = domain.id,
			version = domain.version,
			removed = domain.isRemoved(),
			createdBy = domain.createdBy,
			createdDate = domain.createdDate,
			lastModifiedBy = domain.lastModifiedBy,
			lastModifiedDate = domain.lastModifiedDate
		)
	}

	override fun toDomain(resource: LeaveBalanceDocument): LeaveBalance {
		return LeaveBalance(
			leaveType = resource.leaveType,
			balanceType = resource.balanceType,
			amount = resource.amount,
			year = resource.year,
			userId = resource.userId,
			comment = resource.comment,
			leaveId = resource.leaveId,
			id = resource.id,
			version = resource.version,
			removed = resource.removed,
			createdBy = resource.createdBy,
			createdDate = resource.createdDate,
			lastModifiedBy = resource.lastModifiedBy,
			lastModifiedDate = resource.lastModifiedDate
		)
	}

}
