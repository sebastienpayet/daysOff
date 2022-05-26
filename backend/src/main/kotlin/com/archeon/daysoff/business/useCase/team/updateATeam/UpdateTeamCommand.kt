package com.archeon.daysoff.business.useCase.team.updateATeam

import com.archeon.daysoff.business.useCase.Command

class UpdateTeamCommand(
	val teamId: String,
	val name: String
) : Command
