package com.archeon.daysoff.infrastructure.resource.mongoDb.user

import com.archeon.daysoff.business.domain.user.Role
import com.archeon.daysoff.infrastructure.resource.mongoDb.MongoModel
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "user")
class UserDocument(
	val firstname:String,
	val lastname: String,
	val hasMinorChild: Boolean,
	val teamIds: Set<String>,
	val role: Role,
	val email:String,
	val credential: String,
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
