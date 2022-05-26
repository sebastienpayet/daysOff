package com.archeon.daysoff.infrastructure.resource.mongoDb

import org.springframework.data.annotation.*
import java.time.Instant

abstract class MongoModel(
	@Id
	var id: String? = null,
	var createdDate: Instant? = null,
	var createdBy: String? = null,
	var lastModifiedBy: String? = null,
	var lastModifiedDate: Instant? = null,
	@Version var version: Long = 0,
	var removed: Boolean = false
)
