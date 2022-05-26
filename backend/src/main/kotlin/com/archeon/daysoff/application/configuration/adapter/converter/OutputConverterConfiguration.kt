package com.archeon.daysoff.application.configuration.adapter.converter

import com.archeon.daysoff.business.service.LeaveRequestService
import com.archeon.daysoff.infrastructure.converter.output.leave.LeaveOutputConverter
import com.archeon.daysoff.infrastructure.converter.output.leaveBalance.LeaveBalanceOutputConverter
import com.archeon.daysoff.infrastructure.converter.output.settings.SettingsOutputConverter
import com.archeon.daysoff.infrastructure.converter.output.team.TeamOutputConverter
import com.archeon.daysoff.infrastructure.converter.output.user.ObfuscatedUserOutputConverter
import com.archeon.daysoff.infrastructure.converter.output.user.UserOutputConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OutputConverterConfiguration {

	@Autowired
	private lateinit var leaveRequestService: LeaveRequestService

	@Bean
	fun userOutputConverter(): UserOutputConverter {
		return UserOutputConverter()
	}

	@Bean
	fun obfuscatedUserOutputConverter(): ObfuscatedUserOutputConverter {
		return ObfuscatedUserOutputConverter()
	}

	@Bean
	fun leaveOutputConverter(): LeaveOutputConverter {
		return LeaveOutputConverter(leaveRequestService)
	}

	@Bean
	fun leaveBalanceOutputConverter(): LeaveBalanceOutputConverter {
		return LeaveBalanceOutputConverter()
	}

	@Bean
	fun settingsOutputConverter(): SettingsOutputConverter {
		return SettingsOutputConverter()
	}

	@Bean
	fun teamResourceConverter(): TeamOutputConverter {
		return TeamOutputConverter()
	}

}
