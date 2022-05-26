package com.archeon.daysoff.infrastructure.repository.mongoDb.team

import com.archeon.daysoff.infrastructure.resource.mongoDb.team.TeamDocument
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Profile("mongo")
@Repository
interface NativeMongoTeamRepository : MongoRepository<TeamDocument, String> {
	fun findByName(name: String): TeamDocument?
}
