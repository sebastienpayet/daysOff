package com.archeon.daysoff.infrastructure.converter.output.leaveBalance

import com.archeon.daysoff.business.domain.leave.LeaveBalance
import com.archeon.daysoff.business.util.ErrorUtil.MANDATORY_FIELD
import com.archeon.daysoff.infrastructure.converter.AbstractConverter
import com.archeon.daysoff.infrastructure.resource.output.leaveBalance.LeaveBalanceOutput

class LeaveBalanceOutputConverter : AbstractConverter<LeaveBalance, LeaveBalanceOutput>() {

	override fun toResource(domain: LeaveBalance): LeaveBalanceOutput {
		return LeaveBalanceOutput(
			userId = domain.userId,
			leaveType = domain.leaveType,
			balanceType = domain.balanceType,
			amount = domain.amount,
			year = domain.year,
			leaveId = domain.leaveId,
			comment = domain.comment,
			id = domain.id ?: error(MANDATORY_FIELD),
			createdDate = domain.createdDate ?: error(MANDATORY_FIELD),
			createdBy = domain.createdBy ?: error(MANDATORY_FIELD),
			lastModifiedBy = domain.lastModifiedBy ?: error(MANDATORY_FIELD),
			lastModifiedDate = domain.lastModifiedDate ?: error(MANDATORY_FIELD),
			removed = domain.isRemoved(),
			version = domain.version
		)
	}

	override fun toDomain(resource: LeaveBalanceOutput): LeaveBalance {
		TODO("Not yet implemented")
	}
}
