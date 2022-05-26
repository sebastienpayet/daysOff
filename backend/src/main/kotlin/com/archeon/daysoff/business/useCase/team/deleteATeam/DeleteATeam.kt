package com.archeon.daysoff.business.useCase.team.deleteATeam

import com.archeon.daysoff.business.domain.team.Team
import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.service.TeamService
import com.archeon.daysoff.business.useCase.UseCase

class DeleteATeam(
	private val teamService: TeamService,
	private val authorizationGateway: AuthorizationGateway
) : UseCase<DeleteTeamCommand, Team> {

	override fun handle(command: DeleteTeamCommand): Team {
		authorizationGateway.checkAuthorization(this, command)
		return teamService.deleteTeam(command)
	}
}
