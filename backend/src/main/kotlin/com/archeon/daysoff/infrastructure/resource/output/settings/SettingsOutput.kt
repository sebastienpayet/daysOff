package com.archeon.daysoff.infrastructure.resource.output.settings

import com.archeon.daysoff.infrastructure.resource.output.OutputResource
import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SettingsOutput(
	val publicHolidays: Set<LocalDate>,
	val inputRecoveryInHours: Boolean,
	val numberOfHoursInAWorkingDay: BigDecimal?,
	override val id: String,
	override val createdDate: Instant,
	override val createdBy: String,
	override val lastModifiedBy: String,
	override val lastModifiedDate: Instant,
	override val removed: Boolean,
	override val version: Long?
) : OutputResource
