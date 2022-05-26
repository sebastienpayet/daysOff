package com.archeon.daysoff.acceptance.configuration

import com.archeon.daysoff.acceptance.SharedData
import com.archeon.daysoff.business.port.gateway.AuthenticationGateway
import com.archeon.daysoff.business.port.generator.IdGenerator
import com.archeon.daysoff.business.port.repository.*
import com.archeon.daysoff.business.port.service.CryptService
import com.archeon.daysoff.business.port.service.HashService
import com.archeon.daysoff.business.service.*
import com.archeon.daysoff.infrastructure.generator.UuidGenerator
import com.archeon.daysoff.infrastructure.service.AESCryptService
import com.archeon.daysoff.infrastructure.service.HmacSHA256HashService
import io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Lazy
import org.springframework.context.annotation.Scope
import java.time.Clock

@Scope(SCOPE_CUCUMBER_GLUE)
class CommonConfiguration(
	private val userRepository: UserRepository,
	private val leaveRepository: LeaveRepository,
	private val leaveBalanceRepository: LeaveBalanceRepository,
	private val teamRepository: TeamRepository,
	settingsRepository: SettingsRepository,
	@Lazy
	private val authenticationGateway: AuthenticationGateway
) {
	private val hashService = HmacSHA256HashService()
	private val idGenerator = UuidGenerator()
	private val teamService = TeamService(teamRepository, userRepository, idGenerator, authenticationGateway)
	private val settingsService = SettingsService(settingsRepository, idGenerator, authenticationGateway)

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun sharedData(): SharedData {
		return SharedData()
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun idGenerator(): IdGenerator {
		return UuidGenerator()
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun cryptService(): CryptService {
		return AESCryptService(Clock.systemDefaultZone())
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun hashService(): HashService {
		return hashService
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun teamService(): TeamService {
		return teamService
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun userService(): UserService {
		return UserService(userRepository, idGenerator, hashService, authenticationGateway, teamRepository)
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun settingsService(): SettingsService {
		return settingsService
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun leaveService(): LeaveRequestService {
		return LeaveRequestService(leaveRepository, userRepository, idGenerator, authenticationGateway, settingsService)
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun leaveBalanceService(): LeaveBalanceService {
		return LeaveBalanceService(leaveBalanceRepository, idGenerator, authenticationGateway)
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun leaveRequestService(): LeaveRequestService {
		return LeaveRequestService(leaveRepository, userRepository, idGenerator, authenticationGateway, settingsService)
	}
}
