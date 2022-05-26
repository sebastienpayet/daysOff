package com.archeon.daysoff.infrastructure.controller.team

import com.archeon.daysoff.business.useCase.team.createATeam.CreateTeamCommand
import com.archeon.daysoff.business.useCase.team.deleteATeam.DeleteTeamCommand
import com.archeon.daysoff.business.useCase.team.updateATeam.UpdateTeamCommand
import com.archeon.daysoff.infrastructure.resource.output.team.TeamOutput

interface TeamApi {
	fun listTeams(): Set<TeamOutput>
	fun createATeam(createUserCommand: CreateTeamCommand): TeamOutput
	fun updateATeam(updateUserCommand: UpdateTeamCommand): TeamOutput
	fun deleteATeam(deleteTeamCommand: DeleteTeamCommand): TeamOutput
}
