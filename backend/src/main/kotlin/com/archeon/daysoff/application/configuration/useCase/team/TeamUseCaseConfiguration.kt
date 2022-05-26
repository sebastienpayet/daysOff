package com.archeon.daysoff.application.configuration.useCase.team

import com.archeon.daysoff.business.port.gateway.AuthorizationGateway
import com.archeon.daysoff.business.service.TeamService
import com.archeon.daysoff.business.useCase.team.createATeam.CreateATeam
import com.archeon.daysoff.business.useCase.team.deleteATeam.DeleteATeam
import com.archeon.daysoff.business.useCase.team.listTeams.ListTeams
import com.archeon.daysoff.business.useCase.team.updateATeam.UpdateATeam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TeamUseCaseConfiguration {

	@Autowired
	private lateinit var authorizationGateway: AuthorizationGateway

	@Autowired
	private lateinit var teamService: TeamService

	@Bean
	fun listTeams(): ListTeams {
		return ListTeams(authorizationGateway, teamService)
	}

	@Bean
	fun createATeam(): CreateATeam {
		return CreateATeam(teamService, authorizationGateway)
	}

	@Bean
	fun updateATeam(): UpdateATeam {
		return UpdateATeam(teamService, authorizationGateway)
	}

	@Bean
	fun deleteATeal(): DeleteATeam {
		return DeleteATeam(teamService, authorizationGateway)
	}
}
