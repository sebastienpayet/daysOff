package com.archeon.daysoff.infrastructure.repository.mongoDb.user

import com.archeon.daysoff.business.domain.user.User
import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.util.ErrorUtil.UNKNOWN_USER
import com.archeon.daysoff.infrastructure.converter.mongoDb.user.UserDocumentConverter
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("mongo")
@Component
class MongoDbUserRepository(
	private val repository: NativeMongoUserRepository,
	private val converter: UserDocumentConverter
) : UserRepository {
	override fun findByFirstnameAndLastname(lastname: String, firstname: String): User {
		return repository.findByFirstnameAndLastname(lastname, firstname)
			?.let { document -> converter.toDomain(document) }
			?: error(UNKNOWN_USER)
	}

	override fun findAllByTeamIdsIn(ids: Set<String>): Set<User> {
		return repository.findAllByTeamIds(ids).map { converter.toDomain(it) }.toSet()
	}

	override fun findByEmailAndCredential(email: String, credential: String): User? {
		return repository.findByEmailAndCredential(email, credential)
			?.let { document -> converter.toDomain(document) }
	}

	override fun findByEmail(email: String): User? {
		return repository.findByEmail(email)
			?.let { document -> converter.toDomain(document) }
	}

	override fun save(domain: User): User {
		val document = converter.toResource(domain)
		return converter.toDomain(repository.save(document))
	}

	override fun findAll(): List<User> {
		return repository.findAll().map { converter.toDomain(it) }
	}

	override fun hardDeleteAll() {
		repository.deleteAll()
	}

	override fun findById(id: String): User {
		val document = repository.findById(id).orElseThrow { IllegalArgumentException(UNKNOWN_USER) }
		return converter.toDomain(document)
	}


}
