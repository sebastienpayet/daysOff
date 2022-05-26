package com.archeon.daysoff.infrastructure.converter.output.leave

import com.archeon.daysoff.business.domain.leave.LeaveRequest
import com.archeon.daysoff.business.service.LeaveRequestService
import com.archeon.daysoff.business.util.ErrorUtil.MANDATORY_FIELD
import com.archeon.daysoff.infrastructure.converter.AbstractConverter
import com.archeon.daysoff.infrastructure.resource.output.leave.LeaveOutput
import java.time.LocalTime
import java.time.ZoneOffset

class LeaveOutputConverter(
	private val leaveRequestService: LeaveRequestService
) : AbstractConverter<LeaveRequest, LeaveOutput>() {
	override fun toResource(domain: LeaveRequest): LeaveOutput {
		return LeaveOutput(
			depositDate = domain.depositDate,
			startDate = domain.startDate.atTime(LocalTime.MIN).toInstant(ZoneOffset.UTC),
			fullDayAtStart = domain.fullDayAtStart,
			endDate = domain.endDate.atTime(LocalTime.MAX).toInstant(ZoneOffset.UTC),
			fullDayAtEnd = domain.fullDayAtEnd,
			leaveWorkflows = domain.leaveWorkflows,
			leaveType = domain.leaveType,
			userId = domain.userId,
			comment = domain.comment,
			duration = leaveRequestService.computeLeaveDuration(domain),
			id = domain.id ?: error(MANDATORY_FIELD),
			createdDate = domain.createdDate ?: error(MANDATORY_FIELD),
			createdBy = domain.createdBy ?: error(MANDATORY_FIELD),
			lastModifiedBy = domain.lastModifiedBy ?: error(MANDATORY_FIELD),
			lastModifiedDate = domain.lastModifiedDate ?: error(MANDATORY_FIELD),
			removed = domain.isRemoved(),
			version = domain.version
		)
	}

	override fun toDomain(resource: LeaveOutput): LeaveRequest {
		TODO("Not yet implemented")
	}
}
