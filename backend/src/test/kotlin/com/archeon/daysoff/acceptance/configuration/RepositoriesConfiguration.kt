package com.archeon.daysoff.acceptance.configuration

import com.archeon.daysoff.business.port.repository.*
import com.archeon.daysoff.infrastructure.repository.inMemory.*
import io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope

class RepositoriesConfiguration {

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun userRepository(): UserRepository {
		return InMemoryUserRepository()
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun leaveBalanceRepository(): LeaveBalanceRepository {
		return InMemoryLeaveBalanceRepository()
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun leaveRepository(): LeaveRepository {
		return InMemoryLeaveRepository()
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun teamRepository(): TeamRepository {
		return InMemoryTeamRepository()
	}

	@Bean
	@Scope(SCOPE_CUCUMBER_GLUE)
	fun settingsRepository(): SettingsRepository {
		return InMemorySettingsRepository()
	}
}
