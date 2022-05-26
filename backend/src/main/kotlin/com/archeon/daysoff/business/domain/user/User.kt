package com.archeon.daysoff.business.domain.user

import com.archeon.daysoff.business.domain.DomainModel
import java.time.Instant

open class User(
    val firstname:String,
    val lastname: String,
    val hasMinorChild: Boolean,
    val teamIds: Set<String>,
    val role: Role,
    val email:String,
    val credential: String,
    id: String? = null,
    version: Long = 0,
    removed: Boolean = false,
    createdBy: String? = null,
    createdDate: Instant? = null,
    lastModifiedBy: String? = null,
    lastModifiedDate: Instant? = null
) : DomainModel<User>(
    id = id,
    createdDate = createdDate,
    createdBy = createdBy,
    removed = removed,
    lastModifiedBy = lastModifiedBy,
    lastModifiedDate = lastModifiedDate,
    version = version
) {

    override fun copyDomainData(source: User): User {
        return User(
            firstname = firstname,
            lastname = lastname,
            role = role,
            teamIds = teamIds,
            hasMinorChild = hasMinorChild,
            email = email,
            credential = credential,
            id = source.id,
            version = source.version,
            removed = source.isRemoved(),
            createdBy = source.createdBy,
            createdDate = source.createdDate,
            lastModifiedBy = source.lastModifiedBy,
            lastModifiedDate = source.lastModifiedDate
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (firstname != other.firstname) return false
        if (lastname != other.lastname) return false
        if (hasMinorChild != other.hasMinorChild) return false
        if (teamIds != other.teamIds) return false
        if (role != other.role) return false

        return true
    }

    override fun hashCode(): Int {
        var result = firstname.hashCode()
        result = 31 * result + lastname.hashCode()
        result = 31 * result + hasMinorChild.hashCode()
        result = 31 * result + teamIds.hashCode()
        result = 31 * result + role.hashCode()
        return result
    }


}
