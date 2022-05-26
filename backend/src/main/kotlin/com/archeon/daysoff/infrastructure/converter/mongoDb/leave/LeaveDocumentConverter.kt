package com.archeon.daysoff.infrastructure.converter.mongoDb.leave

import com.archeon.daysoff.business.domain.leave.LeaveRequest
import com.archeon.daysoff.infrastructure.converter.AbstractConverter
import com.archeon.daysoff.infrastructure.resource.mongoDb.leave.LeaveRequestDocument
import org.springframework.stereotype.Component

@Component
class LeaveDocumentConverter : AbstractConverter<LeaveRequest, LeaveRequestDocument>() {
	override fun toResource(domain: LeaveRequest): LeaveRequestDocument {
		return LeaveRequestDocument(
			depositDate = domain.depositDate,
			startDate = domain.startDate,
			endDate = domain.endDate,
			fullDayAtStart = domain.fullDayAtStart,
			fullDayAtEnd = domain.fullDayAtEnd,
			leaveWorkflows = domain.leaveWorkflows,
			leaveType = domain.leaveType,
			userId = domain.userId,
			comment = domain.comment,
			id = domain.id,
			version = domain.version,
			removed = domain.isRemoved(),
			createdBy = domain.createdBy,
			createdDate = domain.createdDate,
			lastModifiedBy = domain.lastModifiedBy,
			lastModifiedDate = domain.lastModifiedDate
		)
	}

	override fun toDomain(resource: LeaveRequestDocument): LeaveRequest {
		return LeaveRequest(
			depositDate = resource.depositDate,
			startDate = resource.startDate,
			endDate = resource.endDate,
			fullDayAtStart = resource.fullDayAtStart,
			fullDayAtEnd = resource.fullDayAtEnd,
			leaveWorkflows = resource.leaveWorkflows,
			leaveType = resource.leaveType,
			userId = resource.userId,
			comment = resource.comment,
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
