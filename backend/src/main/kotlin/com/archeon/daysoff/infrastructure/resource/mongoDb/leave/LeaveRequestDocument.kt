package com.archeon.daysoff.infrastructure.resource.mongoDb.leave

import com.archeon.daysoff.business.domain.leave.LeaveType
import com.archeon.daysoff.business.domain.leave.LeaveWorkflow
import com.archeon.daysoff.infrastructure.resource.mongoDb.MongoModel
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.time.LocalDate

@Document(collection = "leaveRequest")
class LeaveRequestDocument(
    val depositDate: Instant,
    val startDate: LocalDate,
    val fullDayAtStart: Boolean,
    val endDate: LocalDate,
    val fullDayAtEnd: Boolean,
    val leaveWorkflows: Set<LeaveWorkflow>,
    val leaveType: LeaveType,
    val userId: String,
    val comment: String,
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
