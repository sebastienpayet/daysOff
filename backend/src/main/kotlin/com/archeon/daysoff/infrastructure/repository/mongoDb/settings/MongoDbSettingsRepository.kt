package com.archeon.daysoff.infrastructure.repository.mongoDb.settings

import com.archeon.daysoff.business.domain.settings.Settings
import com.archeon.daysoff.business.port.repository.SettingsRepository
import com.archeon.daysoff.business.util.ErrorUtil
import com.archeon.daysoff.infrastructure.converter.mongoDb.settings.SettingsDocumentConverter
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("mongo")
@Component
class MongoDbSettingsRepository(
	private val repository: NativeMongoSettingsRepository,
	private val converter: SettingsDocumentConverter
) : SettingsRepository {
	override fun load(): Settings {
		return converter.toDomain(repository.findAll().first())
	}

	override fun save(domain: Settings): Settings {
		val document = converter.toResource(domain)
		return converter.toDomain(repository.save(document))
	}

	override fun findAll(): List<Settings> {
		return repository.findAll().map { converter.toDomain(it) }
	}

	override fun hardDeleteAll() {
		repository.deleteAll()
	}

	override fun findById(id: String): Settings {
		val document = repository.findById(id).orElseThrow { IllegalArgumentException(ErrorUtil.UNKNOWN_SETTINGS) }
		return converter.toDomain(document)
	}
}
