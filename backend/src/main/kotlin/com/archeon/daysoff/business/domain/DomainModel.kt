package com.archeon.daysoff.business.domain

import java.time.Instant

abstract class DomainModel<T>(
	val id: String? = null,
	val createdDate: Instant? = null,
	val createdBy: String? = null,
	val lastModifiedBy: String? = null,
	val lastModifiedDate: Instant? = null,
	val version: Long = 0,
	private var removed: Boolean = false
) : Domain<T> {
	override fun remove() {
		this.removed = true
	}

	override fun unRemove() {
		this.removed = false
	}

	override fun isRemoved(): Boolean {
		return this.removed
	}
}
