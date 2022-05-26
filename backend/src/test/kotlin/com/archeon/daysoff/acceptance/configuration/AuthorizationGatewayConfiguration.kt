package com.archeon.daysoff.acceptance.configuration

import com.archeon.daysoff.business.port.gateway.AuthenticationGateway
import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.service.LeaveRequestService
import com.archeon.daysoff.infrastructure.gateway.authorization.inMemory.InMemoryAuthorizationGateway
import io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope

@Scope(SCOPE_CUCUMBER_GLUE)
class AuthorizationGatewayConfiguration(
    private val authenticationGateway: AuthenticationGateway,
    private val userRepository: UserRepository,
    private val leaveRequestService: LeaveRequestService,
) {

    @Bean
    @Scope(SCOPE_CUCUMBER_GLUE)
    fun authorizationGateway(): AuthorizationGateway {
        return InMemoryAuthorizationGateway(authenticationGateway, userRepository, leaveRequestService)
    }
}
