package com.archeon.daysoff.application.configuration.useCase.leave

import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.service.LeaveRequestService
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
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LeaveUseCaseConfiguration {

	@Autowired
	private lateinit var authorizationGateway: AuthorizationGateway

	@Autowired
	private lateinit var leaveService: LeaveRequestService

	@Autowired
	private lateinit var createALeaveBalance: CreateALeaveBalance

	@Bean
	fun createALeaveRequest(): CreateALeaveRequest {
		return CreateALeaveRequest(authorizationGateway, leaveService)
	}

	@Bean
	fun listLeaveRequests(): ListLeaveRequests {
		return ListLeaveRequests(authorizationGateway, leaveService)
	}

	@Bean
	fun submitALeaveRequest(): SubmitALeaveRequest {
		return SubmitALeaveRequest(authorizationGateway, leaveService)
	}

	@Bean
	fun refuseALeaveRequest(): RefuseALeaveRequest {
		return RefuseALeaveRequest(authorizationGateway, leaveService)
	}

	@Bean
	fun deleteALeaveRequest(): DeleteALeaveRequest {
		return DeleteALeaveRequest(authorizationGateway, leaveService)
	}

	@Bean
	fun updateALeaveRequest(): UpdateALeaveRequest {
		return UpdateALeaveRequest(authorizationGateway, leaveService)
	}

	@Bean
	fun validateALeaveRequestByService(): ValidateALeaveRequestByService {
		return ValidateALeaveRequestByService(authorizationGateway, leaveService)
	}

	@Bean
	fun validateALeaveRequestByManagement(): ValidateALeaveRequestByManagement {
		return ValidateALeaveRequestByManagement(authorizationGateway, leaveService, createALeaveBalance)
	}

	@Bean
	fun setLeaveRequestAsDraft(): SetLeaveRequestAsDraft {
		return SetLeaveRequestAsDraft(authorizationGateway, leaveService)
	}
}
