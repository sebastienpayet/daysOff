package com.archeon.daysoff.infrastructure.repository.mongoDb.leave

import com.archeon.daysoff.infrastructure.resource.mongoDb.leave.LeaveRequestDocument
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Profile("mongo")
@Repository
interface NativeMongoLeaveRepository: MongoRepository<LeaveRequestDocument, String> {
	fun findByUserIdIn(userIds: List<String>): List<LeaveRequestDocument>
}
