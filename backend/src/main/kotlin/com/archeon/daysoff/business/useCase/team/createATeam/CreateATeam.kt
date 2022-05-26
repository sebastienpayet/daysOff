package com.archeon.daysoff.business.useCase.team.createATeam

import com.archeon.daysoff.business.domain.team.Team
import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.service.TeamService
import com.archeon.daysoff.business.useCase.UseCase

class CreateATeam(
	private val teamService: TeamService,
	private val authorizationGateway: AuthorizationGateway
) : UseCase<CreateTeamCommand, Team> {

	override fun handle(command: CreateTeamCommand): Team {
		authorizationGateway.checkAuthorization(this, command)
		return teamService.createTeam(command)
	}
}
