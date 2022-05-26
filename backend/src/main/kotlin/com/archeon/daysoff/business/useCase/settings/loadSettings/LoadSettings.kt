package com.archeon.daysoff.business.useCase.settings.loadSettings

import com.archeon.daysoff.business.domain.settings.Settings
import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.service.SettingsService
import com.archeon.daysoff.business.useCase.UseCase

class LoadSettings(
	private val authorizationGateway: AuthorizationGateway,
	private val settingsService: SettingsService
) : UseCase<LoadSettingsCommand, Settings> {
	override fun handle(command: LoadSettingsCommand): Settings {
		authorizationGateway.checkAuthorization(this, command)
		return settingsService.load()
	}
}
