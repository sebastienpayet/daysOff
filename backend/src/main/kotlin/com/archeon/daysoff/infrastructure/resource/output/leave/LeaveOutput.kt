package com.archeon.daysoff.infrastructure.resource.output.leave

import com.archeon.daysoff.business.domain.leave.LeaveType
import com.archeon.daysoff.business.domain.leave.LeaveWorkflow
import com.archeon.daysoff.infrastructure.resource.output.OutputResource
import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal
import java.time.Instant

@JsonInclude(JsonInclude.Include.NON_NULL)
data class LeaveOutput(
	val depositDate: Instant,
	val startDate: Instant,
	val fullDayAtStart: Boolean,
	val endDate: Instant,
	val fullDayAtEnd: Boolean,
	val leaveWorkflows: Set<LeaveWorkflow>,
	val leaveType: LeaveType,
	val userId: String,
	val comment: String = "",
	val duration: BigDecimal,
	override val id: String,
	override val createdDate: Instant,
	override val createdBy: String,
	override val lastModifiedBy: String,
	override val lastModifiedDate: Instant,
	override val removed: Boolean,
	override val version: Long?
) : OutputResource
