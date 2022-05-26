package com.archeon.daysoff.infrastructure.repository.mongoDb.user

import com.archeon.daysoff.infrastructure.resource.mongoDb.user.UserDocument
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Profile("mongo")
@Repository
interface NativeMongoUserRepository : MongoRepository<UserDocument, String> {
	fun findByFirstnameAndLastname(lastname: String, firstname: String): UserDocument?
	fun findAllByTeamIds(teamIds: Set<String>): Set<UserDocument>
	fun findByEmailAndCredential(email: String, credential: String): UserDocument?
	fun findByEmail(email: String): UserDocument?
}
