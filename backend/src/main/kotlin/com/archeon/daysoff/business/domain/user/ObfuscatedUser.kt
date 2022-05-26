package com.archeon.daysoff.business.domain.user

import com.archeon.daysoff.business.domain.DomainModel

class ObfuscatedUser(
	val firstname: String? = null,
	val lastname: String? = null,
	val hasMinorChild: Boolean? = null,
	val teamIds: Set<String>? = null,
	val role: Role? = null,
	val email: String? = null,
	val credential: String? = null,
	id: String? = null,
) : DomainModel<ObfuscatedUser>(
	id = id
) {
	override fun copyDomainData(source: ObfuscatedUser): ObfuscatedUser {
		error("Will not be implemented")
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is ObfuscatedUser) return false

		if (firstname != other.firstname) return false
		if (lastname != other.lastname) return false
		if (hasMinorChild != other.hasMinorChild) return false
		if (teamIds != other.teamIds) return false
		if (role != other.role) return false
		if (email != other.email) return false
		if (credential != other.credential) return false

		return true
	}

	override fun hashCode(): Int {
		var result = firstname?.hashCode() ?: 0
		result = 31 * result + (lastname?.hashCode() ?: 0)
		result = 31 * result + (hasMinorChild?.hashCode() ?: 0)
		result = 31 * result + (teamIds?.hashCode() ?: 0)
		result = 31 * result + (role?.hashCode() ?: 0)
		result = 31 * result + (email?.hashCode() ?: 0)
		result = 31 * result + (credential?.hashCode() ?: 0)
		return result
	}
}
