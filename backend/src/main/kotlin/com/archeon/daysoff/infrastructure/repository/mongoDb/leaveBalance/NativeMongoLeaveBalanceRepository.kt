package com.archeon.daysoff.infrastructure.repository.mongoDb.leaveBalance

import com.archeon.daysoff.infrastructure.resource.mongoDb.leaveBalance.LeaveBalanceDocument
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Profile("mongo")
@Repository
interface NativeMongoLeaveBalanceRepository : MongoRepository<LeaveBalanceDocument, String> {
	fun findByUserId(userId: String): List<LeaveBalanceDocument>
	fun findByYearIn(years: Set<Int>): List<LeaveBalanceDocument>
	fun findByUserIdAndYearIn(userId: String, years: Set<Int>): List<LeaveBalanceDocument>
}
