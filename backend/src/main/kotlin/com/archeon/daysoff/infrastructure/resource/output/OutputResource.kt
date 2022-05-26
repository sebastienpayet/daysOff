package com.archeon.daysoff.infrastructure.resource.output

import java.time.Instant

interface OutputResource {
    val id: String?
    val createdDate: Instant?
    val createdBy: String?
    val lastModifiedBy: String?
    val lastModifiedDate: Instant?
    val version: Long?
    val removed: Boolean?
}
