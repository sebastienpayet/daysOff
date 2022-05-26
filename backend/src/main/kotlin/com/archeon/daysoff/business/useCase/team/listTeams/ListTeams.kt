package com.archeon.daysoff.business.useCase.team.listTeams

import com.archeon.daysoff.business.domain.team.Team
import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.service.TeamService
import com.archeon.daysoff.business.useCase.UseCase

class ListTeams(
	private val authorizationGateway: AuthorizationGateway,
	private val teamService: TeamService
) : UseCase<ListTeamCommand, Set<Team>> {
	override fun handle(command: ListTeamCommand): Set<Team> {
		authorizationGateway.checkAuthorization(this, command)
		return teamService.listTeams()
	}
}