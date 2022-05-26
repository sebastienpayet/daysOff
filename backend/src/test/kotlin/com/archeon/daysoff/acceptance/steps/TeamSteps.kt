package com.archeon.daysoff.acceptance.steps

import com.archeon.daysoff.acceptance.SharedData
import com.archeon.daysoff.business.domain.team.Team
import com.archeon.daysoff.business.domain.user.Role
import com.archeon.daysoff.business.domain.user.User
import com.archeon.daysoff.business.port.generator.IdGenerator
import com.archeon.daysoff.business.port.repository.TeamRepository
import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.service.SettingsService
import com.archeon.daysoff.business.service.TeamService
import com.archeon.daysoff.business.useCase.team.createATeam.CreateATeam
import com.archeon.daysoff.business.useCase.team.createATeam.CreateTeamCommand
import com.archeon.daysoff.business.useCase.team.deleteATeam.DeleteATeam
import com.archeon.daysoff.business.useCase.team.deleteATeam.DeleteTeamCommand
import com.archeon.daysoff.business.useCase.team.listTeams.ListTeamCommand
import com.archeon.daysoff.business.useCase.team.listTeams.ListTeams
import com.archeon.daysoff.business.useCase.team.updateATeam.UpdateATeam
import com.archeon.daysoff.business.useCase.team.updateATeam.UpdateTeamCommand
import com.archeon.daysoff.business.util.ErrorUtil.UNKNOWN_TEAM
import com.archeon.daysoff.business.util.ErrorUtil.UNKNOWN_USER
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import java.util.function.Consumer
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TeamSteps(
	userRepository: UserRepository,
	teamRepository: TeamRepository,
	idGenerator: IdGenerator,
	teamService: TeamService,
	settingsService: SettingsService,
	createATeam: CreateATeam,
	deleteATeam: DeleteATeam,
	updateATeam: UpdateATeam,
	sharedData: SharedData,
	listTeams: ListTeams
) : En {

	init {
		Given("des equipes existent") { dataTable: DataTable ->
			val adminTeam = teamRepository.save(Team(id = idGenerator.generate(), name = "admin"))
			userRepository.save(
				User(
					id = idGenerator.generate(),
					firstname = "admin firstname",
					lastname = "admin lastname",
					credential = "credential",
					hasMinorChild = false,
					teamIds = setOf(adminTeam.id ?: ""),
					email = "admin email",
					role = Role.ADMINISTRATOR
				)
			)

			settingsService.initSettings()
			val dataMaps: List<Map<String, String>> = dataTable.asMaps(String::class.java, String::class.java)
			dataMaps.forEach(Consumer { dataMap: Map<String, String> ->
				teamService.update(Team(name = dataMap["nom"] ?: ""))
			})
		}

		When("^je crée une équipe \"([^\"]*)\"$") { name: String ->
			val command = CreateTeamCommand(name = name)

			try {
				createATeam.handle(command)
			} catch (e: Exception) {
				sharedData.currentException = e
			}
		}


		Then("^l'équipe \"([^\"]*)\" existe dans l'application$") { name: String ->
			val checkedTeam = teamRepository.findByNameAndRemoved(name, false)
			assertTrue { checkedTeam != null && !checkedTeam.isRemoved() }
		}

		When("^je supprime l'équipe \"([^\"]*)\"$") { name: String ->
			val team = teamRepository.findByNameAndRemoved(name, false) ?: error(UNKNOWN_TEAM)
			val command = DeleteTeamCommand(team.id ?: "")

			try {
				deleteATeam.handle(command)
			} catch (e: Exception) {
				sharedData.currentException = e
			}
		}

		And("^il n'existe qu'une équipe portant le nom \"([^\"]*)\"$") { name: String ->
			val teams = teamRepository.findAll()
			assertEquals(1, teams.count { it.name == name && !it.isRemoved() })
		}

		And("^je modifie de l'équipe \"([^\"]*)\" par \"([^\"]*)\"$") { name: String, newName: String ->
			val team = teamRepository.findByNameAndRemoved(name, false) ?: error(UNKNOWN_TEAM)
			val command = UpdateTeamCommand(teamId = team.id ?: "", name = newName)

			try {
				updateATeam.handle(command)
			} catch (e: Exception) {
				sharedData.currentException = e
			}
		}

		And("^l'équipe \"([^\"]*)\" n'existe pas dans l'application$") { name: String ->
			val teams = teamRepository.findAll()
			assertEquals(0, teams.count { it.name == name && !it.isRemoved() })
		}

		When("^je liste toutes les équipes$") {
			val command = ListTeamCommand()

			try {
				sharedData.domainDatas = listTeams.handle(command).toList()
			} catch (e: Exception) {
				sharedData.currentException = e
			}
		}

		@Suppress("UNCHECKED_CAST")
		Then("^j'obtiens toutes les équipes$") {
			val loadedTeams = sharedData.domainDatas as List<Team>

			assertEquals(teamRepository.findAll().distinct().size, loadedTeams.distinct().size)
		}

		@Suppress("UNCHECKED_CAST")
		Then("^je n'obtiens que les équipes ou \"([^\"]*)\" \"([^\"]*)\" est affecté$") { lastname: String, firstname: String ->
			val loadedTeams = sharedData.domainDatas as List<Team>
			val user = userRepository.findByFirstnameAndLastname(firstname, lastname) ?: error(UNKNOWN_USER)

			assertTrue { user.teamIds.containsAll(loadedTeams.map { it.id }.toSet()) }
			assertEquals(user.teamIds.size, loadedTeams.size)
		}
	}
}
