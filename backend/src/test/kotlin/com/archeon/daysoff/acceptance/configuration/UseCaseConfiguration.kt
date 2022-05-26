package com.archeon.daysoff.acceptance.configuration

import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.port.service.HashService
import com.archeon.daysoff.business.service.LeaveBalanceService
import com.archeon.daysoff.business.service.LeaveRequestService
import com.archeon.daysoff.business.service.TeamService
import com.archeon.daysoff.business.service.UserService
import com.archeon.daysoff.business.useCase.leave.createALeaveRequest.CreateALeaveRequest
import com.archeon.daysoff.business.useCase.leave.submitALeaveRequest.SubmitALeaveRequest
import com.archeon.daysoff.business.useCase.leave.updateALeaveRequest.UpdateALeaveRequest
import com.archeon.daysoff.business.useCase.leave.validateALeaveRequestByManagement.ValidateALeaveRequestByManagement
import com.archeon.daysoff.business.useCase.leave.validateALeaveRequestByService.ValidateALeaveRequestByService
import com.archeon.daysoff.business.useCase.leaveBalance.createALeaveBalance.CreateALeaveBalance
import com.archeon.daysoff.business.useCase.leaveBalance.listLeaveBalances.ListLeaveBalances
import com.archeon.daysoff.business.useCase.team.createATeam.CreateATeam
import com.archeon.daysoff.business.useCase.team.deleteATeam.DeleteATeam
import com.archeon.daysoff.business.useCase.team.listTeams.ListTeams
import com.archeon.daysoff.business.useCase.team.updateATeam.UpdateATeam
import com.archeon.daysoff.business.useCase.user.createAUser.CreateAUser
import com.archeon.daysoff.business.useCase.user.deleteAUser.DeleteAUser
import com.archeon.daysoff.business.useCase.user.listObfuscatedUsers.ListAllObfuscatedUsers
import com.archeon.daysoff.business.useCase.user.listUsers.ListAllUsers
import com.archeon.daysoff.business.useCase.user.updateAUser.UpdateAUser
import io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope

class UseCaseConfiguration {

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun createAUser(
		authorizationGateway: AuthorizationGateway,
		userRepository: UserRepository,
		hashService: HashService,
		userService: UserService
	): CreateAUser {
		return CreateAUser(userRepository, authorizationGateway, hashService, userService)
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun updateAUser(
		authorizationGateway: AuthorizationGateway,
		userRepository: UserRepository, hashService: HashService, userService: UserService
	): UpdateAUser {
		return UpdateAUser(userRepository, authorizationGateway, hashService, userService)
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun deleteAUser(
		authorizationGateway: AuthorizationGateway,
		userRepository: UserRepository,
		userService: UserService
	): DeleteAUser {
		return DeleteAUser(userRepository, authorizationGateway, userService)
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun createALeaveBalance(
		authorizationGateway: AuthorizationGateway,
		leaveBalanceService: LeaveBalanceService,
	): CreateALeaveBalance {
		return CreateALeaveBalance(authorizationGateway, leaveBalanceService)
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun listLeaveBalances(
		authorizationGateway: AuthorizationGateway,
		leaveBalanceService: LeaveBalanceService
	): ListLeaveBalances {
		return ListLeaveBalances(authorizationGateway, leaveBalanceService)
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun listAllUsers(
		authorizationGateway: AuthorizationGateway,
		userService: UserService
	): ListAllUsers {
		return ListAllUsers(authorizationGateway, userService)
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun listAllObfuscatedUsers(
		authorizationGateway: AuthorizationGateway,
		userService: UserService
	): ListAllObfuscatedUsers {
		return ListAllObfuscatedUsers(authorizationGateway, userService)
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun createATeam(
		authorizationGateway: AuthorizationGateway,
		teamService: TeamService
	): CreateATeam {
		return CreateATeam(teamService, authorizationGateway)
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun deleteATeam(
		authorizationGateway: AuthorizationGateway,
		teamService: TeamService
	): DeleteATeam {
		return DeleteATeam(teamService, authorizationGateway)
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun updateATeam(
		authorizationGateway: AuthorizationGateway,
		teamService: TeamService
	): UpdateATeam {
		return UpdateATeam(teamService, authorizationGateway)
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun listTeams(
		authorizationGateway: AuthorizationGateway,
		teamService: TeamService
	): ListTeams {
		return ListTeams(authorizationGateway, teamService)
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun createALeaveRequest(
		authorizationGateway: AuthorizationGateway,
		leaveRequestService: LeaveRequestService
	): CreateALeaveRequest {
		return CreateALeaveRequest(authorizationGateway, leaveRequestService)
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun submitALeaveRequest(
		authorizationGateway: AuthorizationGateway,
		leaveRequestService: LeaveRequestService
	): SubmitALeaveRequest {
		return SubmitALeaveRequest(authorizationGateway, leaveRequestService)
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun updateALeaveRequest(
		authorizationGateway: AuthorizationGateway,
		leaveRequestService: LeaveRequestService
	): UpdateALeaveRequest {
		return UpdateALeaveRequest(authorizationGateway, leaveRequestService)
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun validateALeaveRequestByService(
		authorizationGateway: AuthorizationGateway,
		leaveRequestService: LeaveRequestService
	): ValidateALeaveRequestByService {
		return ValidateALeaveRequestByService(authorizationGateway, leaveRequestService)
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun validateALeaveRequestByManagement(
		authorizationGateway: AuthorizationGateway,
		leaveRequestService: LeaveRequestService,
		createALeaveBalance: CreateALeaveBalance
	): ValidateALeaveRequestByManagement {
		return ValidateALeaveRequestByManagement(authorizationGateway, leaveRequestService, createALeaveBalance)
	}
}
