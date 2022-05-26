package com.archeon.daysoff.application.configuration.adapter.gateway

import com.archeon.daysoff.business.port.gateway.AuthenticationGateway
import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.service.LeaveRequestService
import com.archeon.daysoff.infrastructure.gateway.authorization.inMemory.InMemoryAuthorizationGateway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AuthorizationGatewayConfiguration {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var leaveRequestService: LeaveRequestService

    @Autowired
    private lateinit var authenticationGateway: AuthenticationGateway

    @Bean
    fun authorizationGateway(): AuthorizationGateway {
        return InMemoryAuthorizationGateway(authenticationGateway, userRepository, leaveRequestService)
    }
}
