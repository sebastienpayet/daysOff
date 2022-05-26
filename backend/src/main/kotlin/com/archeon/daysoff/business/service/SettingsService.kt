package com.archeon.daysoff.business.service

import com.archeon.daysoff.business.domain.settings.Settings
import com.archeon.daysoff.business.port.gateway.AuthenticationGateway
import com.archeon.daysoff.business.port.generator.IdGenerator
import com.archeon.daysoff.business.port.repository.SettingsRepository
import java.math.BigDecimal
import java.math.MathContext
import java.time.Instant
import java.time.LocalDate

class SettingsService(
	private val settingsRepository: SettingsRepository,
	private val idGenerator: IdGenerator,
	private val authenticationGateway: AuthenticationGateway
) {

	fun initSettings() {
		if (settingsRepository.findAll().isEmpty()) {
			val fakeUserId = idGenerator.generate()
			val now = Instant.now()

			// init settings on french public holidays directly in code for the moment
			// for the next 5 years, for update, go in database
			val settings = Settings(
				id = idGenerator.generate(),
				createdBy = fakeUserId,
				createdDate = now,
				version = 0,
				removed = false,
				lastModifiedBy = fakeUserId,
				lastModifiedDate = now,
				inputRecoveryInHours = true,
				numberOfHoursInAWorkingDay = BigDecimal(7.4).round(MathContext(2)),
				publicHolidays = setOf(
					LocalDate.of(2025, 1, 1),
					LocalDate.of(2025, 4, 21),
					LocalDate.of(2025, 1, 1),
					LocalDate.of(2025, 4, 21),
					LocalDate.of(2025, 5, 1),
					LocalDate.of(2025, 5, 8),
					LocalDate.of(2025, 5, 29),
					LocalDate.of(2025, 6, 9),
					LocalDate.of(2025, 7, 14),
					LocalDate.of(2025, 8, 15),
					LocalDate.of(2025, 11, 1),
					LocalDate.of(2025, 11, 11),
					LocalDate.of(2025, 12, 25),
					LocalDate.of(2024, 1, 1),
					LocalDate.of(2024, 4, 1),
					LocalDate.of(2024, 5, 1),
					LocalDate.of(2024, 5, 8),
					LocalDate.of(2024, 5, 9),
					LocalDate.of(2024, 5, 20),
					LocalDate.of(2024, 7, 14),
					LocalDate.of(2024, 8, 15),
					LocalDate.of(2024, 11, 1),
					LocalDate.of(2024, 11, 11),
					LocalDate.of(2024, 12, 25),
					LocalDate.of(2023, 1, 1),
					LocalDate.of(2023, 4, 10),
					LocalDate.of(2023, 5, 1),
					LocalDate.of(2023, 5, 8),
					LocalDate.of(2023, 5, 18),
					LocalDate.of(2023, 5, 29),
					LocalDate.of(2023, 7, 14),
					LocalDate.of(2023, 8, 15),
					LocalDate.of(2023, 11, 1),
					LocalDate.of(2023, 11, 11),
					LocalDate.of(2023, 12, 25),
					LocalDate.of(2022, 1, 1),
					LocalDate.of(2022, 4, 18),
					LocalDate.of(2022, 5, 1),
					LocalDate.of(2022, 5, 8),
					LocalDate.of(2022, 5, 26),
					LocalDate.of(2022, 6, 6),
					LocalDate.of(2022, 7, 14),
					LocalDate.of(2022, 8, 15),
					LocalDate.of(2022, 11, 1),
					LocalDate.of(2022, 11, 11),
					LocalDate.of(2022, 12, 25),
					LocalDate.of(2021, 1, 1),
					LocalDate.of(2021, 4, 5),
					LocalDate.of(2021, 5, 1),
					LocalDate.of(2021, 5, 8),
					LocalDate.of(2021, 5, 13),
					LocalDate.of(2021, 5, 24),
					LocalDate.of(2021, 7, 14),
					LocalDate.of(2021, 8, 15),
					LocalDate.of(2021, 11, 1),
					LocalDate.of(2021, 11, 11),
					LocalDate.of(2021, 12, 25)
				)
			)
			settingsRepository.save(settings)
		}
	}

	private fun updateSettings(settings: Settings): Settings {

		val currentId = settings.id ?: idGenerator.generate()
		val createdDate = settings.createdDate ?: Instant.now()
		val currentUserId = authenticationGateway.getAuthenticatedUser().id.toString()
		val createdBy = settings.createdBy ?: currentUserId

		val settingsToSave = Settings(
			publicHolidays = settings.publicHolidays,
			id = currentId,
			createdDate = createdDate,
			createdBy = createdBy,
			lastModifiedBy = currentUserId,
			lastModifiedDate = Instant.now(),
			removed = settings.isRemoved(),
			version = settings.version
		)

		return settingsRepository.save(settingsToSave)
	}

	fun load(): Settings {
		return settingsRepository.load()
	}
}
