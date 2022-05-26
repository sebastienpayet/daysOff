package com.archeon.daysoff.infrastructure.converter.output.team

import com.archeon.daysoff.business.domain.team.Team
import com.archeon.daysoff.business.port.converter.ResourceConverter
import com.archeon.daysoff.business.util.ErrorUtil
import com.archeon.daysoff.infrastructure.resource.output.team.TeamOutput

class TeamOutputConverter : ResourceConverter<Team, TeamOutput> {
	override fun toResource(domain: Team): TeamOutput {
		return TeamOutput(
			name = domain.name,
			id = domain.id ?: error(ErrorUtil.MANDATORY_FIELD),
			createdDate = domain.createdDate ?: error(ErrorUtil.MANDATORY_FIELD),
			createdBy = domain.createdBy ?: error(ErrorUtil.MANDATORY_FIELD),
			lastModifiedBy = domain.lastModifiedBy ?: error(ErrorUtil.MANDATORY_FIELD),
			lastModifiedDate = domain.lastModifiedDate ?: error(ErrorUtil.MANDATORY_FIELD),
			removed = domain.isRemoved(),
			version = domain.version
		)
	}

	override fun toDomain(resource: TeamOutput): Team {
		error("Not yet implemented")
	}

}
