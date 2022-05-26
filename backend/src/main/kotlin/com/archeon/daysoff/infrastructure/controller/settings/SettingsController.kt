package com.archeon.daysoff.infrastructure.controller.settings

import com.archeon.daysoff.business.useCase.settings.loadSettings.LoadSettings
import com.archeon.daysoff.business.useCase.settings.loadSettings.LoadSettingsCommand
import com.archeon.daysoff.infrastructure.converter.output.settings.SettingsOutputConverter
import com.archeon.daysoff.infrastructure.resource.output.settings.SettingsOutput
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("settings")
class SettingsController(
	private val loadSettings: LoadSettings,
	private val converter: SettingsOutputConverter
) : SettingsApi {

	@GetMapping("loadSettings")
	override fun loadSettings(): SettingsOutput {
		return converter.toResource(loadSettings.handle(LoadSettingsCommand()))
	}
}
