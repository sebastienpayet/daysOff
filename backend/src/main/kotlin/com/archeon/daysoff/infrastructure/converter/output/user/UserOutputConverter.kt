package com.archeon.daysoff.infrastructure.converter.output.user

import com.archeon.daysoff.business.domain.user.User
import com.archeon.daysoff.business.port.converter.ResourceConverter
import com.archeon.daysoff.infrastructure.resource.output.user.UserOutput

class UserOutputConverter: ResourceConverter<User, UserOutput> {
    override fun toResource(domain: User): UserOutput {
        return UserOutput(
            firstname = domain.firstname,
            lastname = domain.lastname,
            hasMinorChild = domain.hasMinorChild,
            teamIds = domain.teamIds,
            role = domain.role,
            email = domain.email,
            id = domain.id ?: error("mandatory field"),
            createdDate = domain.createdDate ?: error("mandatory field"),
            createdBy = domain.createdBy ?: error("mandatory field"),
            lastModifiedBy = domain.lastModifiedBy ?: error("mandatory field"),
            lastModifiedDate = domain.lastModifiedDate ?: error("mandatory field"),
            removed = domain.isRemoved(),
            version = domain.version
        )
    }

    override fun toDomain(resource: UserOutput): User {
        TODO("Not yet implemented")
    }
}
