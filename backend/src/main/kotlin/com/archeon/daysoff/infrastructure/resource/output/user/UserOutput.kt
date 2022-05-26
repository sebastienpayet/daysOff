package com.archeon.daysoff.infrastructure.resource.output.user

import com.archeon.daysoff.business.domain.user.Role
import com.archeon.daysoff.infrastructure.resource.output.OutputResource
import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Instant

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserOutput(
    val firstname: String? = null,
    val lastname: String? = null,
    val hasMinorChild: Boolean? = null,
    val teamIds: Set<String>? = null,
    val role: Role? = null,
    val email: String? = null,
    override val id: String? = null,
    override val createdDate: Instant? = null,
    override val createdBy: String? = null,
    override val lastModifiedBy: String? = null,
    override val lastModifiedDate: Instant? = null,
    override val removed: Boolean? = null,
    override val version: Long? = null
) : OutputResource
