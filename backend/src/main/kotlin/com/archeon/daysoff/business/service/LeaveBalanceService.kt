package com.archeon.daysoff.business.service

import com.archeon.daysoff.business.domain.leave.BalanceType
import com.archeon.daysoff.business.domain.leave.LeaveBalance
import com.archeon.daysoff.business.domain.leave.LeaveType
import com.archeon.daysoff.business.domain.user.Role
import com.archeon.daysoff.business.port.gateway.AuthenticationGateway
import com.archeon.daysoff.business.port.generator.IdGenerator
import com.archeon.daysoff.business.port.repository.LeaveBalanceRepository
import com.archeon.daysoff.business.useCase.leaveBalance.createALeaveBalance.CreateALeaveBalanceCommand
import com.archeon.daysoff.business.useCase.leaveBalance.listLeaveBalances.ListLeaveBalancesCommand
import java.time.Instant

class LeaveBalanceService(
	private val leaveBalanceRepository: LeaveBalanceRepository,
	private val idGenerator: IdGenerator,
	private val authenticationGateway: AuthenticationGateway
) {

	private fun updateLeaveBalance(leaveBalance: LeaveBalance): LeaveBalance {

		val currentId = leaveBalance.id ?: idGenerator.generate()
		val createdDate = leaveBalance.createdDate ?: Instant.now()
		val currentUserId = authenticationGateway.getAuthenticatedUser().id.toString()
		val createdBy = leaveBalance.createdBy ?: currentUserId

		val leaveBalanceToSave = LeaveBalance(
			leaveId = leaveBalance.leaveId,
			leaveType = leaveBalance.leaveType,
			balanceType = leaveBalance.balanceType,
			amount = leaveBalance.amount,
			year = leaveBalance.year,
			userId = leaveBalance.userId,
			id = currentId,
			createdDate = createdDate,
			createdBy = createdBy,
			lastModifiedBy = currentUserId,
			lastModifiedDate = Instant.now(),
			removed = leaveBalance.isRemoved(),
			comment = leaveBalance.comment,
			version = leaveBalance.version
		)

		return leaveBalanceRepository.save(leaveBalanceToSave)
	}

	fun createLeaveBalance(command: CreateALeaveBalanceCommand): LeaveBalance {
		val newLeaveBalance = LeaveBalance(
			leaveId = command.leaveId,
			leaveType = LeaveType.valueOf(command.leaveType.trim()),
			balanceType = BalanceType.valueOf(command.balanceType.trim()),
			amount = command.amount,
			year = command.year,
			userId = command.userId,
			comment = command.comment ?: ""
		)

		return updateLeaveBalance(newLeaveBalance)
	}

	fun loadReadableLeaves(command: ListLeaveBalancesCommand): List<LeaveBalance> {
		val user = authenticationGateway.getAuthenticatedUser()

		return if (user.role == Role.ADMINISTRATOR) {
			leaveBalanceRepository.findByYearIn(command.years)
		} else {
			leaveBalanceRepository.findByUserIdAndYearIn(user.id ?: "", command.years)
		}
	}
}
