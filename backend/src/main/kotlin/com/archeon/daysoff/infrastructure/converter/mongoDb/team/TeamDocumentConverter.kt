package com.archeon.daysoff.infrastructure.converter.mongoDb.team

import com.archeon.daysoff.business.domain.team.Team
import com.archeon.daysoff.infrastructure.converter.AbstractConverter
import com.archeon.daysoff.infrastructure.resource.mongoDb.team.TeamDocument
import org.springframework.stereotype.Component

@Component
class TeamDocumentConverter : AbstractConverter<Team, TeamDocument>() {
	override fun toResource(domain: Team): TeamDocument {
		return TeamDocument(
			name = domain.name,
			id = domain.id,
			version = domain.version,
			removed = domain.isRemoved(),
			createdBy = domain.createdBy,
			createdDate = domain.createdDate,
			lastModifiedBy = domain.lastModifiedBy,
			lastModifiedDate = domain.lastModifiedDate
		)
	}

	override fun toDomain(resource: TeamDocument): Team {
		return Team(
			name = resource.name,
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
