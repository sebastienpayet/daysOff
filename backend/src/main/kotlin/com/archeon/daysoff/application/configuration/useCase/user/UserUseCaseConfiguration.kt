package com.archeon.daysoff.application.configuration.useCase.user

import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.port.service.HashService
import com.archeon.daysoff.business.service.UserService
import com.archeon.daysoff.business.useCase.user.createAUser.CreateAUser
import com.archeon.daysoff.business.useCase.user.listObfuscatedUsers.ListAllObfuscatedUsers
import com.archeon.daysoff.business.useCase.user.listUsers.ListAllUsers
import com.archeon.daysoff.business.useCase.user.updateAUser.UpdateAUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserUseCaseConfiguration {

	@Autowired
	private lateinit var userRepository: UserRepository

	@Autowired
	private lateinit var authorizationGateway: AuthorizationGateway

	@Autowired
	private lateinit var hashService: HashService

	@Autowired
	private lateinit var userService: UserService

	@Bean
	fun createAUser(): CreateAUser {
		return CreateAUser(userRepository, authorizationGateway, hashService, userService)
	}

	@Bean
	fun updateAUser(): UpdateAUser {
		return UpdateAUser(userRepository, authorizationGateway, hashService, userService)
	}

	@Bean
	fun listAllUsers(): ListAllUsers {
		return ListAllUsers(authorizationGateway, userService)
	}

	@Bean
	fun listObfuscatedUsers(): ListAllObfuscatedUsers {
		return ListAllObfuscatedUsers(authorizationGateway, userService)
	}
}
