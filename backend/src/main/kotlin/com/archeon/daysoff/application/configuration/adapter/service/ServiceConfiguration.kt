package com.archeon.daysoff.application.configuration.adapter.service

import com.archeon.daysoff.business.port.gateway.AuthenticationGateway
import com.archeon.daysoff.business.port.generator.IdGenerator
import com.archeon.daysoff.business.port.repository.*
import com.archeon.daysoff.business.port.service.CryptService
import com.archeon.daysoff.business.port.service.HashService
import com.archeon.daysoff.business.service.*
import com.archeon.daysoff.infrastructure.service.AESCryptService
import com.archeon.daysoff.infrastructure.service.HmacSHA256HashService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import java.time.Clock

@Configuration
class ServiceConfiguration {

	@Autowired
	private lateinit var leaveRepository: LeaveRepository

	@Autowired
	private lateinit var leaveBalanceRepository: LeaveBalanceRepository

	@Autowired
	private lateinit var userRepository: UserRepository

	@Autowired
	private lateinit var teamRepository: TeamRepository

	@Autowired
	private lateinit var settingsRepository: SettingsRepository

	@Autowired
	private lateinit var idGenerator: IdGenerator

	@Autowired
	private lateinit var authenticationGateway: AuthenticationGateway

	private val hashService = HmacSHA256HashService()

	@Bean
	fun teamService(): TeamService {
		return TeamService(teamRepository, userRepository, idGenerator, authenticationGateway)
	}

	@Bean
	fun hashService(): HashService {
		return hashService
	}

	@Bean
	fun cryptService(): CryptService {
		return AESCryptService(Clock.systemDefaultZone())
	}

	@Bean
	@Lazy
	fun leaveService(): LeaveRequestService {
		return LeaveRequestService(leaveRepository, userRepository, idGenerator, authenticationGateway, settingsService())
	}

	@Bean
	fun userService(): UserService {
		return UserService(userRepository, idGenerator, hashService, authenticationGateway, teamRepository)
	}

	@Bean
	fun leaveBalanceService(): LeaveBalanceService {
		return LeaveBalanceService(leaveBalanceRepository, idGenerator, authenticationGateway)
	}

	@Bean
	fun settingsService(): SettingsService {
		return SettingsService(settingsRepository, idGenerator, authenticationGateway)
	}
}
