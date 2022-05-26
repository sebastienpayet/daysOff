package com.archeon.daysoff.application.configuration.useCase.leaveBalance

import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.service.LeaveBalanceService
import com.archeon.daysoff.business.useCase.leaveBalance.createALeaveBalance.CreateALeaveBalance
import com.archeon.daysoff.business.useCase.leaveBalance.listLeaveBalances.ListLeaveBalances
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LeaveBalanceUseCaseConfiguration {

	@Autowired
	private lateinit var authorizationGateway: AuthorizationGateway

	@Autowired
	private lateinit var leaveBalanceService: LeaveBalanceService

	@Bean
	fun createALeaveBalance(): CreateALeaveBalance {
		return CreateALeaveBalance(authorizationGateway, leaveBalanceService)
	}

	@Bean
	fun listLeaveBalances(): ListLeaveBalances {
		return ListLeaveBalances(authorizationGateway, leaveBalanceService)
	}
}
