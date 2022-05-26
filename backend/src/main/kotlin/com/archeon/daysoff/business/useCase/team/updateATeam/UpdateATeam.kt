package com.archeon.daysoff.business.useCase.team.updateATeam

import com.archeon.daysoff.business.domain.team.Team
import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.service.TeamService
import com.archeon.daysoff.business.useCase.UseCase

class UpdateATeam(
	private val teamService: TeamService,
	private val authorizationGateway: AuthorizationGateway
) : UseCase<UpdateTeamCommand, Team> {

	override fun handle(command: UpdateTeamCommand): Team {
		authorizationGateway.checkAuthorization(this, command)

		// check that user exists
		val currentTeam = teamService.findById(command.teamId)

		var newTeam = Team(
			name = command.name.trim()
		)

		newTeam = newTeam.copyDomainData(currentTeam)
		return teamService.update(newTeam)
	}
}
