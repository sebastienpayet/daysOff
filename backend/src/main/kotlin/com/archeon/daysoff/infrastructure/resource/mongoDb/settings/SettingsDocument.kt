package com.archeon.daysoff.infrastructure.resource.mongoDb.settings

import com.archeon.daysoff.infrastructure.resource.mongoDb.MongoModel
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate

@Document(collection = "settings")
class SettingsDocument(
    val publicHolidays: Set<LocalDate>,
    val inputRecoveryInHours: Boolean,
    val numberOfHoursInAWorkingDay: BigDecimal?,
    id: String?,
    version: Long,
    removed: Boolean,
    createdBy: String?,
    createdDate: Instant?,
    lastModifiedBy: String?,
    lastModifiedDate: Instant?
) : MongoModel(
    id = id,
    removed = removed,
    createdBy = createdBy,
    createdDate = createdDate,
    lastModifiedBy = lastModifiedBy,
    lastModifiedDate = lastModifiedDate,
    version = version
)
