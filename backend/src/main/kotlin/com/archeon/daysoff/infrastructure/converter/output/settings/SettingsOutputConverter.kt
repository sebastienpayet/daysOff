package com.archeon.daysoff.infrastructure.converter.output.settings

import com.archeon.daysoff.business.domain.settings.Settings
import com.archeon.daysoff.business.util.ErrorUtil.MANDATORY_FIELD
import com.archeon.daysoff.infrastructure.converter.AbstractConverter
import com.archeon.daysoff.infrastructure.resource.output.settings.SettingsOutput

class SettingsOutputConverter : AbstractConverter<Settings, SettingsOutput>() {
	override fun toResource(domain: Settings): SettingsOutput {
		return SettingsOutput(
			publicHolidays = domain.publicHolidays,
			inputRecoveryInHours = domain.inputRecoveryInHours,
			numberOfHoursInAWorkingDay = domain.numberOfHoursInAWorkingDay,
			id = domain.id ?: error(MANDATORY_FIELD),
			createdDate = domain.createdDate ?: error(MANDATORY_FIELD),
			createdBy = domain.createdBy ?: error(MANDATORY_FIELD),
			lastModifiedBy = domain.lastModifiedBy ?: error(MANDATORY_FIELD),
			lastModifiedDate = domain.lastModifiedDate ?: error(MANDATORY_FIELD),
			removed = domain.isRemoved(),
			version = domain.version
		)
	}

	override fun toDomain(resource: SettingsOutput): Settings {
		TODO("Not yet implemented")
	}
}
