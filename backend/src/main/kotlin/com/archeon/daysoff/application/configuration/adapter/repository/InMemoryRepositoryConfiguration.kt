package com.archeon.daysoff.application.configuration.adapter.repository

import com.archeon.daysoff.business.port.repository.*
import com.archeon.daysoff.infrastructure.repository.inMemory.*
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("memory")
@EnableAutoConfiguration(exclude = [MongoAutoConfiguration::class, MongoDataAutoConfiguration::class])
class InMemoryRepositoryConfiguration {

	@Bean
	fun userRepository(): UserRepository {
		return InMemoryUserRepository()
	}

	@Bean
	fun leaveRepository(): LeaveRepository {
		return InMemoryLeaveRepository()
	}

	@Bean
	fun leaveBalanceRepository(): LeaveBalanceRepository {
		return InMemoryLeaveBalanceRepository()
	}

	@Bean
	fun teamRepository(): TeamRepository {
		return InMemoryTeamRepository()
	}

	@Bean
	fun settingsRepository(): SettingsRepository {
		return InMemorySettingsRepository()
	}
}
