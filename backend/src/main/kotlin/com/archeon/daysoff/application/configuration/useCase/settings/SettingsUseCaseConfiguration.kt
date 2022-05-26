package com.archeon.daysoff.application.configuration.useCase.settings

import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.service.SettingsService
import com.archeon.daysoff.business.useCase.settings.loadSettings.LoadSettings
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SettingsUseCaseConfiguration {

	@Autowired
	private lateinit var authorizationGateway: AuthorizationGateway

	@Autowired
	private lateinit var settingsService: SettingsService

	@Bean
	fun loadSettings(): LoadSettings {
		return LoadSettings(authorizationGateway, settingsService)
	}

}
