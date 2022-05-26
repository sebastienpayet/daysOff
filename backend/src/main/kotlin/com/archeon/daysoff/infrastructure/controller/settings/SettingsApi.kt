package com.archeon.daysoff.infrastructure.controller.settings

import com.archeon.daysoff.infrastructure.resource.output.settings.SettingsOutput

interface SettingsApi {
	fun loadSettings(): SettingsOutput
}
