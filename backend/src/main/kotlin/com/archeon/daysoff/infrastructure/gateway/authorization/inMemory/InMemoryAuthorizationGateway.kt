package com.archeon.daysoff.infrastructure.gateway.authorization.inMemory

import com.archeon.daysoff.business.domain.user.Role
import com.archeon.daysoff.business.port.gateway.AuthenticationGateway
import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.service.LeaveRequestService
import com.archeon.daysoff.business.useCase.Command
import com.archeon.daysoff.business.useCase.UseCase
import com.archeon.daysoff.business.useCase.UseCaseAuthorization
import com.archeon.daysoff.business.useCase.leave.createALeaveRequest.CreateALeaveRequest
import com.archeon.daysoff.business.useCase.leave.deleteALeaveRequest.DeleteALeaveRequest
import com.archeon.daysoff.business.useCase.leave.listLeaves.ListLeaveRequests
import com.archeon.daysoff.business.useCase.leave.refuseALeaveRequest.RefuseALeaveRequest
import com.archeon.daysoff.business.useCase.leave.setLeaveRequestAsDraft.SetLeaveRequestAsDraft
import com.archeon.daysoff.business.useCase.leave.submitALeaveRequest.SubmitALeaveRequest
import com.archeon.daysoff.business.useCase.leave.updateALeaveRequest.UpdateALeaveRequest
import com.archeon.daysoff.business.useCase.leave.validateALeaveRequestByManagement.ValidateALeaveRequestByManagement
import com.archeon.daysoff.business.useCase.leave.validateALeaveRequestByService.ValidateALeaveRequestByService
import com.archeon.daysoff.business.useCase.leaveBalance.createALeaveBalance.CreateALeaveBalance
import com.archeon.daysoff.business.useCase.leaveBalance.listLeaveBalances.ListLeaveBalances
import com.archeon.daysoff.business.useCase.settings.loadSettings.LoadSettings
import com.archeon.daysoff.business.useCase.team.createATeam.CreateATeam
import com.archeon.daysoff.business.useCase.team.deleteATeam.DeleteATeam
import com.archeon.daysoff.business.useCase.team.listTeams.ListTeams
import com.archeon.daysoff.business.useCase.team.updateATeam.UpdateATeam
import com.archeon.daysoff.business.useCase.user.createAUser.CreateAUser
import com.archeon.daysoff.business.useCase.user.deleteAUser.DeleteAUser
import com.archeon.daysoff.business.useCase.user.listObfuscatedUsers.ListAllObfuscatedUsers
import com.archeon.daysoff.business.useCase.user.listUsers.ListAllUsers
import com.archeon.daysoff.business.useCase.user.updateAUser.UpdateAUser
import com.archeon.daysoff.business.util.ErrorUtil.ACCESS_FORBIDDEN
import com.archeon.daysoff.infrastructure.gateway.authorization.inMemory.useCase.*
import kotlin.reflect.KClass

class InMemoryAuthorizationGateway(
	override val authenticationGateway: AuthenticationGateway,
	override val userRepository: UserRepository,
	override val leaveRequestService: LeaveRequestService,
) : AuthorizationGateway {

	companion object {
		val EVERYBODY = setOf(Role.ADMINISTRATOR, Role.TEAM_MANAGER, Role.USER)
	}

	private val securityMatrix: Map<KClass<*>, Set<Role>> =
		mapOf(
			Pair(CreateAUser::class, setOf(Role.ADMINISTRATOR)),
			Pair(UpdateAUser::class, setOf(Role.ADMINISTRATOR)),
			Pair(DeleteAUser::class, setOf(Role.ADMINISTRATOR)),
			Pair(CreateALeaveBalance::class, setOf(Role.ADMINISTRATOR)),
			Pair(ValidateALeaveRequestByManagement::class, setOf(Role.ADMINISTRATOR)),
			Pair(ListAllUsers::class, setOf(Role.ADMINISTRATOR)),
			Pair(CreateATeam::class, setOf(Role.ADMINISTRATOR)),
			Pair(UpdateATeam::class, setOf(Role.ADMINISTRATOR)),
			Pair(DeleteATeam::class, setOf(Role.ADMINISTRATOR)),

			Pair(RefuseALeaveRequest::class, setOf(Role.ADMINISTRATOR, Role.TEAM_MANAGER)),
			Pair(ValidateALeaveRequestByService::class, setOf(Role.ADMINISTRATOR, Role.TEAM_MANAGER)),

			Pair(ListAllObfuscatedUsers::class, EVERYBODY),
			Pair(ListTeams::class, EVERYBODY),
			Pair(ListLeaveRequests::class, EVERYBODY),
			Pair(CreateALeaveRequest::class, EVERYBODY),
			Pair(DeleteALeaveRequest::class, EVERYBODY),
			Pair(SubmitALeaveRequest::class, EVERYBODY),
			Pair(SetLeaveRequestAsDraft::class, EVERYBODY),
			Pair(UpdateALeaveRequest::class, EVERYBODY),
			Pair(ListLeaveBalances::class, EVERYBODY),
			Pair(LoadSettings::class, EVERYBODY),
		)

	private val specificCheckMatrix: Map<KClass<*>, UseCaseAuthorization<out Command>> =
		mapOf(
			Pair(
				CreateALeaveRequest::class,
				CreateALeaveRequestAuthorization(userRepository)
			),
			Pair(
				DeleteALeaveRequest::class,
				DeleteALeaveRequestAuthorization(leaveRequestService)
			),
			Pair(
				SubmitALeaveRequest::class,
				SubmitALeaveRequestAuthorization(leaveRequestService, userRepository)
			),
			Pair(
				RefuseALeaveRequest::class,
				RefuseALeaveRequestAuthorization(leaveRequestService, userRepository)
			),
			Pair(
				ValidateALeaveRequestByService::class,
				ValidateALeaveRequestByServiceAuthorization(leaveRequestService, userRepository)
			),
			Pair(
				ValidateALeaveRequestByManagement::class,
				ValidateALeaveRequestByManagementAuthorization(leaveRequestService, userRepository)
			),
			Pair(
				SetLeaveRequestAsDraft::class,
				SetLeaveRequestAsDraftAuthorization(leaveRequestService, userRepository)
			),
			Pair(
				UpdateALeaveRequest::class,
				UpdateALeaveRequestAuthorization(leaveRequestService, userRepository)
			)
		)

	override fun checkAuthorization(
		useCase: UseCase<*, *>,
		command: Command
	) {
		val currentUser = authenticationGateway.getAuthenticatedUser()
		val check = securityMatrix[useCase::class]?.contains(currentUser.role) ?: false

		// Role access check
		if (!check) {
			throw IllegalAccessException(ACCESS_FORBIDDEN)
		}

		// Specific use case check
		@Suppress("UNCHECKED_CAST")
		if (specificCheckMatrix[useCase::class] is UseCaseAuthorization<out Command>) {
			// must do an unsafe cast to get correct command type in doCheck call...
			(specificCheckMatrix[useCase::class] as UseCaseAuthorization<Command>).doCheck(currentUser, command)
		}
	}
}
