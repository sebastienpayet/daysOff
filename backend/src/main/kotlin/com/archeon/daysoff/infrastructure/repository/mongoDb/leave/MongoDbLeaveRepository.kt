package com.archeon.daysoff.infrastructure.repository.mongoDb.leave

import com.archeon.daysoff.business.domain.leave.LeaveRequest
import com.archeon.daysoff.business.port.repository.LeaveRepository
import com.archeon.daysoff.business.util.ErrorUtil.UNKNOWN_LEAVE
import com.archeon.daysoff.infrastructure.converter.mongoDb.leave.LeaveDocumentConverter
import com.archeon.daysoff.infrastructure.resource.mongoDb.leave.LeaveRequestDocument
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component

@Profile("mongo")
@Component
class MongoDbLeaveRepository(
	private val repository: NativeMongoLeaveRepository,
	private val converter: LeaveDocumentConverter,
	private val mongoTemplate: MongoTemplate
) : LeaveRepository {

	override fun findOverlappingLeaveByUserId(leaveRequest: LeaveRequest): List<LeaveRequest> {
		val query = Query()
		query.addCriteria(
			Criteria().andOperator(
				Criteria.where("id").ne(leaveRequest.id),
				Criteria.where("userId").`is`(leaveRequest.userId),
				Criteria().orOperator(
					Criteria.where("startDate").gte(leaveRequest.startDate).lte(leaveRequest.endDate),
					Criteria.where("endDate").gte(leaveRequest.startDate).lte(leaveRequest.endDate),
					Criteria().andOperator(
						Criteria.where("starDate").lte(leaveRequest.startDate),
						Criteria.where("endDate").gte(leaveRequest.endDate)
					)
				)
			)
		)

		return mongoTemplate.find(query, LeaveRequestDocument::class.java).map { converter.toDomain(it) }
	}

	override fun findAllByUserIdIn(userIds: List<String>): List<LeaveRequest> {
		return repository.findByUserIdIn(userIds).map { converter.toDomain(it) }
	}

	override fun save(domain: LeaveRequest): LeaveRequest {
		val document = converter.toResource(domain)
		return converter.toDomain(repository.save(document))
	}

	override fun findAll(): List<LeaveRequest> {
		return repository.findAll().map { converter.toDomain(it) }
	}

	override fun hardDeleteAll() {
		repository.deleteAll()
	}

	override fun findById(id: String): LeaveRequest {
		val document = repository.findById(id).orElseThrow { IllegalArgumentException(UNKNOWN_LEAVE) }
		return converter.toDomain(document)
	}
}
