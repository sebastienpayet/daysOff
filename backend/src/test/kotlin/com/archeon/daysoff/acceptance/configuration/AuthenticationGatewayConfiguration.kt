package com.archeon.daysoff.acceptance.configuration

import com.archeon.daysoff.business.port.gateway.AuthenticationGateway
import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.port.service.CryptService
import com.archeon.daysoff.infrastructure.gateway.authentication.inMemory.InMemoryAuthenticationGateway
import io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Lazy
import org.springframework.context.annotation.Scope

@Scope(SCOPE_CUCUMBER_GLUE)
class AuthenticationGatewayConfiguration(
	@Lazy private val userRepository: UserRepository,
	private val cryptService: CryptService
) {

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun authenticationGateway(): AuthenticationGateway {
		return InMemoryAuthenticationGateway(userRepository, cryptService)
	}
}