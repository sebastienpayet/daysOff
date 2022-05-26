package com.archeon.daysoff.infrastructure.repository.mongoDb.leaveBalance

import com.archeon.daysoff.business.domain.leave.LeaveBalance
import com.archeon.daysoff.business.port.repository.LeaveBalanceRepository
import com.archeon.daysoff.business.util.ErrorUtil
import com.archeon.daysoff.infrastructure.converter.mongoDb.leaveBalance.LeaveBalanceDocumentConverter
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("mongo")
@Component
class MongoDbLeaveBalanceRepository(
	private val repository: NativeMongoLeaveBalanceRepository,
	private val converter: LeaveBalanceDocumentConverter
) : LeaveBalanceRepository {
	override fun findByUserId(userId: String): List<LeaveBalance> {
		return repository.findByUserId(userId).map { converter.toDomain(it) }
	}

	override fun findByYearIn(years: Set<Int>): List<LeaveBalance> {
		return repository.findByYearIn(years).map { converter.toDomain(it) }
	}

	override fun findByUserIdAndYearIn(userId: String, years: Set<Int>): List<LeaveBalance> {
		return repository.findByUserIdAndYearIn(userId, years).map { converter.toDomain(it) }
	}

	override fun save(domain: LeaveBalance): LeaveBalance {
		val document = converter.toResource(domain)
		return converter.toDomain(repository.save(document))
	}

	override fun findAll(): List<LeaveBalance> {
		return repository.findAll().map { converter.toDomain(it) }
	}

	override fun hardDeleteAll() {
		repository.deleteAll()
	}

	override fun findById(id: String): LeaveBalance {
		val document = repository.findById(id).orElseThrow { IllegalArgumentException(ErrorUtil.UNKNOWN_LEAVE) }
		return converter.toDomain(document)
	}
}
