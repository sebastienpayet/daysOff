package com.archeon.daysoff.infrastructure.controller.team

import com.archeon.daysoff.business.useCase.team.createATeam.CreateATeam
import com.archeon.daysoff.business.useCase.team.createATeam.CreateTeamCommand
import com.archeon.daysoff.business.useCase.team.deleteATeam.DeleteATeam
import com.archeon.daysoff.business.useCase.team.deleteATeam.DeleteTeamCommand
import com.archeon.daysoff.business.useCase.team.listTeams.ListTeamCommand
import com.archeon.daysoff.business.useCase.team.listTeams.ListTeams
import com.archeon.daysoff.business.useCase.team.updateATeam.UpdateATeam
import com.archeon.daysoff.business.useCase.team.updateATeam.UpdateTeamCommand
import com.archeon.daysoff.infrastructure.converter.output.team.TeamOutputConverter
import com.archeon.daysoff.infrastructure.resource.output.team.TeamOutput
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("team")
class TeamController(
	private val listTeams: ListTeams,
	private val createATeam: CreateATeam,
	private val updateATeam: UpdateATeam,
	private val deleteATeam: DeleteATeam,
	private val converter: TeamOutputConverter
) : TeamApi {

	@GetMapping("listTeams")
	override fun listTeams(): Set<TeamOutput> {
		return listTeams.handle(ListTeamCommand()).map { team -> converter.toResource(team) }.toSet()
	}

	@PostMapping
	override fun createATeam(@RequestBody createUserCommand: CreateTeamCommand): TeamOutput {
		return converter.toResource(createATeam.handle(createUserCommand))
	}

	@PatchMapping
	override fun updateATeam(@RequestBody updateUserCommand: UpdateTeamCommand): TeamOutput {
		return converter.toResource(updateATeam.handle(updateUserCommand))
	}

	@PatchMapping("delete")
	override fun deleteATeam(@RequestBody deleteTeamCommand: DeleteTeamCommand): TeamOutput {
		return converter.toResource(deleteATeam.handle(deleteTeamCommand))
	}

}
