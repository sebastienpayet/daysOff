package com.archeon.daysoff.infrastructure.resource.output.leaveBalance

import com.archeon.daysoff.business.domain.leave.BalanceType
import com.archeon.daysoff.business.domain.leave.LeaveType
import com.archeon.daysoff.infrastructure.resource.output.OutputResource
import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal
import java.time.Instant

@JsonInclude(JsonInclude.Include.NON_NULL)
data class LeaveBalanceOutput(
	val leaveType: LeaveType,
	val balanceType: BalanceType,
	val amount: BigDecimal,
	val year: Int,
	val userId: String,
	val comment: String = "",
	val leaveId: String? = "", // have to be empty for CREDIT, must not be empty to DEBIT
	override val id: String,
	override val createdDate: Instant,
	override val createdBy: String,
	override val lastModifiedBy: String,
	override val lastModifiedDate: Instant,
	override val removed: Boolean,
	override val version: Long?
) : OutputResource
