package com.archeon.daysoff.infrastructure.converter.output.user

import com.archeon.daysoff.business.domain.user.ObfuscatedUser
import com.archeon.daysoff.business.port.converter.ResourceConverter
import com.archeon.daysoff.infrastructure.resource.output.user.UserOutput

class ObfuscatedUserOutputConverter: ResourceConverter<ObfuscatedUser, UserOutput> {
    override fun toResource(domain: ObfuscatedUser): UserOutput {
        return UserOutput(
            firstname = domain.firstname,
            lastname = domain.lastname,
            hasMinorChild = domain.hasMinorChild,
            teamIds = domain.teamIds,
            role = domain.role,
            email = domain.email,
            id = domain.id ?: error("mandatory field"),
        )
    }

    override fun toDomain(resource: UserOutput): ObfuscatedUser {
        TODO("Not yet implemented")
    }
}
