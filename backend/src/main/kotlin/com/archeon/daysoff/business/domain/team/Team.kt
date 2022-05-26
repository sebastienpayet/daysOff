package com.archeon.daysoff.business.domain.team

import com.archeon.daysoff.business.domain.DomainModel
import com.archeon.daysoff.business.util.StringUtil.removeNonSpacingMarksAndLowerCase
import java.time.Instant

class Team(
    val name: String,
    id: String? = null,
    version: Long = 0,
    removed: Boolean = false,
    createdBy: String? = null,
    createdDate: Instant? = null,
    lastModifiedBy: String? = null,
    lastModifiedDate: Instant? = null
) : DomainModel<Team>(
    id = id,
    createdDate = createdDate,
    createdBy = createdBy,
    removed = removed,
    lastModifiedBy = lastModifiedBy,
    lastModifiedDate = lastModifiedDate,
    version = version
) {

    override fun copyDomainData(source: Team): Team {
        return Team(
            name = name,
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

        other as Team

        if (name.removeNonSpacingMarksAndLowerCase() != other.name.removeNonSpacingMarksAndLowerCase()) return false

        return true
    }

    override fun hashCode(): Int {
        return name.removeNonSpacingMarksAndLowerCase().hashCode()
    }
}
