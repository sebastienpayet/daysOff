package com.archeon.daysoff.business.domain.leave

import java.time.Instant

class LeaveWorkflow(
    val date: Instant,
    val userId: String,
    val leaveStatus: LeaveStatus,
    val comment: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LeaveWorkflow

        if (date != other.date) return false
        if (userId != other.userId) return false
        if (leaveStatus != other.leaveStatus) return false

        return true
    }

    override fun hashCode(): Int {
        var result = date.hashCode()
        result = 31 * result + userId.hashCode()
        result = 31 * result + leaveStatus.hashCode()
        return result
    }
}