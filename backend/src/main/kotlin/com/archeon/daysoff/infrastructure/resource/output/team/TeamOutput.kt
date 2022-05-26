package com.archeon.daysoff.infrastructure.resource.output.team

import com.archeon.daysoff.infrastructure.resource.output.OutputResource
import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Instant

@JsonInclude(JsonInclude.Include.NON_NULL)
data class TeamOutput(
	val name: String,
	override val id: String?,
	override val createdDate: Instant?,
	override val createdBy: String?,
	override val lastModifiedBy: String?,
	override val lastModifiedDate: Instant?,
	override val version: Long?,
	override val removed: Boolean?
) : OutputResource
