package com.archeon.daysoff.infrastructure.resource.mongoDb.leaveBalance

import com.archeon.daysoff.business.domain.leave.BalanceType
import com.archeon.daysoff.business.domain.leave.LeaveType
import com.archeon.daysoff.infrastructure.resource.mongoDb.MongoModel
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.Instant

@Document(collection = "leaveBalance")
class LeaveBalanceDocument(
    val leaveType: LeaveType,
    val balanceType: BalanceType,
    val amount: BigDecimal,
    val year: Int,
    val userId: String,
    val comment: String = "",
    val leaveId: String? = "", // have to be empty for CREDIT, must not be empty to DEBIT
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
