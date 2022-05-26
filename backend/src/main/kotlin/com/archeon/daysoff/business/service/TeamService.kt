package com.archeon.daysoff.business.service

import com.archeon.daysoff.business.domain.team.Team
import com.archeon.daysoff.business.domain.user.Role
import com.archeon.daysoff.business.port.gateway.AuthenticationGateway
import com.archeon.daysoff.business.port.generator.IdGenerator
import com.archeon.daysoff.business.port.repository.TeamRepository
import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.useCase.team.createATeam.CreateTeamCommand
import com.archeon.daysoff.business.useCase.team.deleteATeam.DeleteTeamCommand
import com.archeon.daysoff.business.util.ErrorUtil.TEAM_ALREADY_EXISTS
import com.archeon.daysoff.business.util.ErrorUtil.TEAM_WITH_USERS
import com.archeon.daysoff.business.util.ErrorUtil.UNKNOWN_USER
import java.time.Instant

class TeamService(
	private val teamRepository: TeamRepository,
	private val userRepository: UserRepository,
	private val idGenerator: IdGenerator,
	private val authenticationGateway: AuthenticationGateway
) {

	fun createTeam(
		command: CreateTeamCommand
	): Team {
		val teamName = command.name.trim()

		// check if the name doesn't already exists
		listTeams().find { team -> team.name.equals(teamName, ignoreCase = true) && !team.isRemoved() }?.apply {
			throw IllegalArgumentException(TEAM_ALREADY_EXISTS)
		}

		val team = Team(
			name = teamName
		)
		return update(team)
	}

	fun initData() {
		if (teamRepository.findAll().isEmpty()) {
			// create default team
			val dummyId = idGenerator.generate()

			teamRepository.save(
				Team(
					id = idGenerator.generate(),
					name = DEFAULT_TEAM,
					createdDate = Instant.now(),
					lastModifiedDate = Instant.now(),
					createdBy = dummyId,
					lastModifiedBy = dummyId
				)
			)
		}
	}

	fun deleteTeam(command: DeleteTeamCommand): Team {
		val usersOnThisTeam = userRepository.findAllByTeamIdsIn(setOf(command.teamId))

		if (usersOnThisTeam.isNotEmpty()) {
			throw IllegalArgumentException(TEAM_WITH_USERS)
		}

		val targetTeam = teamRepository.findById(command.teamId) ?: throw IllegalArgumentException(UNKNOWN_USER)
		targetTeam.remove()
		return update(targetTeam)
	}

	fun update(team: Team): Team {
		// check if the name doesn't already exist on another team
		listTeams().find { it ->
			it.name.equals(team.name, ignoreCase = true)
					&& team.id != it.id
					&& !team.isRemoved()
		}?.apply {
			throw IllegalArgumentException(TEAM_ALREADY_EXISTS)
		}

		val currentId = team.id ?: idGenerator.generate()
		val createdDate = team.createdDate ?: Instant.now()
		val currentUserId = authenticationGateway.getAuthenticatedUser().id.toString()
		val createdBy = team.createdBy ?: currentUserId

		val teamToSave = Team(
			name = team.name,
			id = currentId,
			createdDate = createdDate,
			createdBy = createdBy,
			lastModifiedBy = currentUserId,
			lastModifiedDate = Instant.now(),
			removed = team.isRemoved(),
			version = team.version
		)

		return teamRepository.save(teamToSave)
	}

	fun listTeams(): Set<Team> {
		val currentUser = authenticationGateway.getAuthenticatedUser()
		val allTeams = teamRepository.findAll().toSet()
		return if (currentUser.role == Role.ADMINISTRATOR) {
			allTeams
		} else {
			allTeams.filter { team -> currentUser.teamIds.contains(team.id) }.toSet()
		}
	}

	fun findById(id: String): Team {
		return teamRepository.findById(id) ?: throw IllegalArgumentException(UNKNOWN_USER)
	}

	companion object {
		const val DEFAULT_TEAM = "Administration"
	}
}
