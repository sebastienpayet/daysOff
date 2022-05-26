package com.archeon.daysoff.business.useCase.team.createATeam

import com.archeon.daysoff.business.useCase.Command

data class CreateTeamCommand(val name: String): Command
