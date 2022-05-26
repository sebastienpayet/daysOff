package com.archeon.daysoff.infrastructure.converter.mongoDb.user

import com.archeon.daysoff.business.domain.user.User
import com.archeon.daysoff.infrastructure.converter.AbstractConverter
import com.archeon.daysoff.infrastructure.resource.mongoDb.user.UserDocument
import org.springframework.stereotype.Component

@Component
class UserDocumentConverter : AbstractConverter<User, UserDocument>() {
	override fun toResource(domain: User): UserDocument {
		return UserDocument(
			firstname = domain.firstname,
			lastname = domain.lastname,
			hasMinorChild = domain.hasMinorChild,
			teamIds = domain.teamIds,
			role = domain.role,
			email = domain.email,
			credential = domain.credential,
			id = domain.id,
			version = domain.version,
			removed = domain.isRemoved(),
			createdBy = domain.createdBy,
			createdDate = domain.createdDate,
			lastModifiedBy = domain.lastModifiedBy,
			lastModifiedDate = domain.lastModifiedDate
		)
	}

	override fun toDomain(resource: UserDocument): User {
		return User(
			firstname = resource.firstname,
			lastname = resource.lastname,
			hasMinorChild = resource.hasMinorChild,
			teamIds = resource.teamIds,
			role = resource.role,
			email = resource.email,
			credential = resource.credential,
			id = resource.id,
			version = resource.version,
			removed = resource.removed,
			createdBy = resource.createdBy,
			createdDate = resource.createdDate,
			lastModifiedBy = resource.lastModifiedBy,
			lastModifiedDate = resource.lastModifiedDate
		)
	}
}
