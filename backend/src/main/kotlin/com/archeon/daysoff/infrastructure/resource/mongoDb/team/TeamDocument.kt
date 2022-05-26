package com.archeon.daysoff.infrastructure.resource.mongoDb.team

import com.archeon.daysoff.infrastructure.resource.mongoDb.MongoModel
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "team")
class TeamDocument(
	val name: String,
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
