package com.archeon.daysoff.business.domain.settings

import com.archeon.daysoff.business.domain.DomainModel
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate

class Settings(
	val publicHolidays: Set<LocalDate>,
	val inputRecoveryInHours: Boolean = false,
	val numberOfHoursInAWorkingDay: BigDecimal? = null,
	id: String? = null,
	version: Long = 0,
	removed: Boolean = false,
	createdBy: String? = null,
	createdDate: Instant? = null,
	lastModifiedBy: String? = null,
	lastModifiedDate: Instant? = null
) : DomainModel<Settings>(
	id = id,
	createdDate = createdDate,
	createdBy = createdBy,
	removed = removed,
	lastModifiedBy = lastModifiedBy,
	lastModifiedDate = lastModifiedDate,
	version = version
) {

	override fun copyDomainData(source: Settings): Settings {
		return Settings(
			publicHolidays = source.publicHolidays,
			inputRecoveryInHours = source.inputRecoveryInHours,
			numberOfHoursInAWorkingDay = source.numberOfHoursInAWorkingDay,
			id = source.id,
			version = source.version,
			removed = source.isRemoved(),
			createdBy = source.createdBy,
			createdDate = source.createdDate,
			lastModifiedBy = source.lastModifiedBy,
			lastModifiedDate = source.lastModifiedDate
		)
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is Settings) return false

		if (publicHolidays != other.publicHolidays) return false
		if (inputRecoveryInHours != other.inputRecoveryInHours) return false

		return true
	}

	override fun hashCode(): Int {
		var result = publicHolidays.hashCode()
		result = 31 * result + inputRecoveryInHours.hashCode()
		return result
	}
}
