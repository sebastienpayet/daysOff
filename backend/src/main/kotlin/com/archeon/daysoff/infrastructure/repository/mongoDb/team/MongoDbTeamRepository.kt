package com.archeon.daysoff.infrastructure.repository.mongoDb.team

import com.archeon.daysoff.business.domain.team.Team
import com.archeon.daysoff.business.port.repository.TeamRepository
import com.archeon.daysoff.business.util.ErrorUtil.UNKNOWN_TEAM
import com.archeon.daysoff.infrastructure.converter.mongoDb.team.TeamDocumentConverter
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("mongo")
@Component
class MongoDbTeamRepository(
	private val repository: NativeMongoTeamRepository,
	private val converter: TeamDocumentConverter
) : TeamRepository {
	override fun findByNameAndRemoved(name: String, removed: Boolean): Team {
		return repository.findByName(name)?.let { document -> converter.toDomain(document) }
			?: error(UNKNOWN_TEAM)
	}

	override fun save(domain: Team): Team {
		val document = converter.toResource(domain)
		return converter.toDomain(repository.save(document))
	}

	override fun findAll(): List<Team> {
		return repository.findAll().map { converter.toDomain(it) }
	}

	override fun hardDeleteAll() {
		repository.deleteAll()
	}

	override fun findById(id: String): Team {
		val document = repository.findById(id).orElseThrow { IllegalArgumentException(UNKNOWN_TEAM) }
		return converter.toDomain(document)
	}
}
