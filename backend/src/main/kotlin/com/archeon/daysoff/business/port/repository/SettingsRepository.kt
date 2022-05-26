package com.archeon.daysoff.business.port.repository

import com.archeon.daysoff.business.domain.settings.Settings

interface SettingsRepository: Repository<Settings> {
	fun load(): Settings
}
