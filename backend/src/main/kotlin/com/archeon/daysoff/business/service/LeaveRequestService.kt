package com.archeon.daysoff.business.service

import com.archeon.daysoff.business.domain.leave.LeaveRequest
import com.archeon.daysoff.business.domain.leave.LeaveStatus
import com.archeon.daysoff.business.domain.leave.LeaveWorkflow
import com.archeon.daysoff.business.domain.user.Role
import com.archeon.daysoff.business.port.gateway.AuthenticationGateway
import com.archeon.daysoff.business.port.generator.IdGenerator
import com.archeon.daysoff.business.port.repository.LeaveRepository
import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.useCase.leave.createALeaveRequest.CreateALeaveRequestCommand
import com.archeon.daysoff.business.util.ErrorUtil.A_VALID_REQUEST_EXISTS_IN_THE_PERIOD
import com.archeon.daysoff.business.util.ErrorUtil.UNKNOWN_LEAVE
import com.archeon.daysoff.business.util.ErrorUtil.UNKNOWN_USER
import java.math.BigDecimal
import java.time.DayOfWeek
import java.time.Instant

class LeaveRequestService(
	private val leaveRepository: LeaveRepository,
	private val userRepository: UserRepository,
	private val idGenerator: IdGenerator,
	private val authenticationGateway: AuthenticationGateway,
	private val settingsService: SettingsService
) {
	fun updateWorkflow(
		leaveId: String,
		leaveStatus: LeaveStatus,
		comment: String
	): LeaveRequest {
		val leave = leaveRepository.findById(leaveId) ?: throw IllegalArgumentException(UNKNOWN_LEAVE)
		val user = authenticationGateway.getAuthenticatedUser()

		val updatedWorkflow = leave.leaveWorkflows.toMutableSet()
		updatedWorkflow.add(
			LeaveWorkflow(
				date = Instant.now(),
				userId = user.id.toString(),
				leaveStatus = leaveStatus,
				comment = comment
			)
		)

		var leaveRequest = LeaveRequest(
			depositDate = leave.depositDate,
			startDate = leave.startDate,
			fullDayAtStart = leave.fullDayAtStart,
			endDate = leave.endDate,
			fullDayAtEnd = leave.fullDayAtEnd,
			leaveWorkflows = updatedWorkflow,
			leaveType = leave.leaveType,
			userId = leave.userId,
			comment = leave.comment
		)

		leaveRequest = leaveRequest.copyDomainData(leave)
		return leaveRepository.save(leaveRequest)
	}

	fun loadReadableLeaves(): List<LeaveRequest> {
		val user = authenticationGateway.getAuthenticatedUser()
		return when (user.role) {
			Role.USER -> {
				user.id?.let { id -> leaveRepository.findAllByUserIdIn(listOf(id)).filter { !it.isRemoved() } }
					?: emptyList()
			}
			Role.TEAM_MANAGER -> {
				val userIds: List<String> = userRepository.findAllByTeamIdsIn(user.teamIds).mapNotNull { it.id }
				return leaveRepository.findAllByUserIdIn(userIds).filter { !it.isRemoved() }
			}
			Role.ADMINISTRATOR -> {
				return leaveRepository.findAll().filter { !it.isRemoved() }
			}
		}
	}

	fun createLeave(
		command: CreateALeaveRequestCommand
	): LeaveRequest {
		val currentUser = authenticationGateway.getAuthenticatedUser()
		val targetUser = userRepository.findById(command.userId) ?: throw IllegalArgumentException(UNKNOWN_USER)

		val leaveRequest = LeaveRequest(
			depositDate = Instant.now(),
			startDate = command.startDate,
			fullDayAtStart = command.fullDayAtStart,
			endDate = command.endDate,
			fullDayAtEnd = command.fullDayAtEnd,
			leaveWorkflows = setOf(
				LeaveWorkflow(
					date = Instant.now(),
					userId = currentUser.id.toString(),
					leaveStatus = LeaveStatus.DRAFT
				)
			),
			leaveType = command.leaveType,
			userId = targetUser.id.toString(),
			comment = command.comment ?: ""
		)
		return updateLeave(leaveRequest)
	}

	fun deleteLeave(leaveId: String): LeaveRequest {
		val targetLeave = leaveRepository.findById(leaveId) ?: throw IllegalArgumentException(UNKNOWN_LEAVE)
		targetLeave.remove()
		return updateLeave(targetLeave)
	}

	fun updateLeave(leaveRequest: LeaveRequest): LeaveRequest {

		val currentId = leaveRequest.id ?: idGenerator.generate()
		val createdDate = leaveRequest.createdDate ?: Instant.now()
		val currentUserId = authenticationGateway.getAuthenticatedUser().id.toString()
		val createdBy = leaveRequest.createdBy ?: currentUserId

		val leaveRequestToSave = LeaveRequest(
			depositDate = leaveRequest.depositDate,
			startDate = leaveRequest.startDate,
			fullDayAtStart = leaveRequest.fullDayAtStart,
			endDate = leaveRequest.endDate,
			fullDayAtEnd = leaveRequest.fullDayAtEnd,
			userId = leaveRequest.userId,
			leaveType = leaveRequest.leaveType,
			leaveWorkflows = leaveRequest.leaveWorkflows,
			id = currentId,
			createdDate = createdDate,
			createdBy = createdBy,
			lastModifiedBy = currentUserId,
			lastModifiedDate = Instant.now(),
			removed = leaveRequest.isRemoved(),
			comment = leaveRequest.comment,
			version = leaveRequest.version
		)

		return leaveRepository.save(leaveRequestToSave)
	}

	fun findById(leaveId: String): LeaveRequest {
		return leaveRepository.findById(leaveId) ?: throw IllegalArgumentException(UNKNOWN_LEAVE)
	}

	fun checkIsNoOtherRequestInPeriod(leaveRequest: LeaveRequest) {
		val overlappingLeaveRequests =
			leaveRepository.findOverlappingLeaveByUserId(leaveRequest)
		require(overlappingLeaveRequests.none {
			listOf(LeaveStatus.MANAGEMENT_APPROVED, LeaveStatus.SERVICE_APPROVED, LeaveStatus.SUBMITTED)
				.contains(it.getCurrentStatus())
		})
		{ A_VALID_REQUEST_EXISTS_IN_THE_PERIOD }
	}

	fun computeLeaveDuration(leave: LeaveRequest): BigDecimal {
		val publicHolidays = settingsService.load().publicHolidays
		var numberOfDays: BigDecimal = BigDecimal.ZERO

		var currentDay = leave.startDate
		while (currentDay.isBefore(leave.endDate) || currentDay.isEqual(leave.endDate)) {
			if (!listOf(DayOfWeek.SUNDAY, DayOfWeek.SATURDAY).contains(currentDay.dayOfWeek)
				&& !publicHolidays.contains(currentDay)
			) {
				numberOfDays++
			}
			currentDay = currentDay.plusDays(1)
		}

		if (!leave.fullDayAtStart) {
			numberOfDays = numberOfDays.minus(BigDecimal(0.5))
		}
		if (!leave.fullDayAtEnd && (leave.startDate != leave.endDate || leave.fullDayAtStart)) {
			numberOfDays = numberOfDays.minus(BigDecimal(0.5))
		}
		return numberOfDays.max(BigDecimal.ZERO)
	}
}
