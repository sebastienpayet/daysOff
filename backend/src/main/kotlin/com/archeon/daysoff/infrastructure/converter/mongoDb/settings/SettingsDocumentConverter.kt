package com.archeon.daysoff.infrastructure.converter.mongoDb.settings

import com.archeon.daysoff.business.domain.settings.Settings
import com.archeon.daysoff.infrastructure.converter.AbstractConverter
import com.archeon.daysoff.infrastructure.resource.mongoDb.settings.SettingsDocument
import org.springframework.stereotype.Component

@Component
class SettingsDocumentConverter : AbstractConverter<Settings, SettingsDocument>() {
	override fun toResource(domain: Settings): SettingsDocument {
		return SettingsDocument(
			publicHolidays = domain.publicHolidays,
			inputRecoveryInHours = domain.inputRecoveryInHours,
			numberOfHoursInAWorkingDay = domain.numberOfHoursInAWorkingDay,
			id = domain.id,
			version = domain.version,
			removed = domain.isRemoved(),
			createdBy = domain.createdBy,
			createdDate = domain.createdDate,
			lastModifiedBy = domain.lastModifiedBy,
			lastModifiedDate = domain.lastModifiedDate
		)
	}

	override fun toDomain(resource: SettingsDocument): Settings {
		return Settings(
			publicHolidays = resource.publicHolidays,
			inputRecoveryInHours = resource.inputRecoveryInHours,
			numberOfHoursInAWorkingDay = resource.numberOfHoursInAWorkingDay,
			id = resource.id,
			version = resource.version,
			removed = resource.removed,
			createdBy = resource.createdBy,
			createdDate = resource.createdDate,
			lastModifiedBy = resource.lastModifiedBy,
			lastModifiedDate = resource.lastModifiedDate
		)
	}
}
