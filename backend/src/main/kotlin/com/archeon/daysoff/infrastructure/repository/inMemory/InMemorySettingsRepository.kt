package com.archeon.daysoff.infrastructure.repository.inMemory

import com.archeon.daysoff.business.domain.settings.Settings
import com.archeon.daysoff.business.port.repository.SettingsRepository

class InMemorySettingsRepository : SettingsRepository {

	private var settings = emptySet<Settings>()

	override fun load(): Settings {
		return settings.first()
	}

	override fun save(domain: Settings): Settings {
		settings = setOf(domain)
		return settings.first()
	}

	override fun findAll(): List<Settings> {
		return settings.toList()
	}

	override fun hardDeleteAll() {
		this.settings = emptySet()
	}

	override fun findById(id: String): Settings {
		error("Not yet implemented")
	}
}
