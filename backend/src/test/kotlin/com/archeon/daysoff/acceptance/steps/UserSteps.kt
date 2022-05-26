package com.archeon.daysoff.acceptance.steps

import com.archeon.daysoff.acceptance.SharedData
import com.archeon.daysoff.business.domain.user.ObfuscatedUser
import com.archeon.daysoff.business.domain.user.Role
import com.archeon.daysoff.business.domain.user.User
import com.archeon.daysoff.business.port.gateway.AuthenticationGateway
import com.archeon.daysoff.business.port.repository.TeamRepository
import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.service.UserService
import com.archeon.daysoff.business.useCase.user.createAUser.CreateAUser
import com.archeon.daysoff.business.useCase.user.createAUser.CreateUserCommand
import com.archeon.daysoff.business.useCase.user.deleteAUser.DeleteAUser
import com.archeon.daysoff.business.useCase.user.deleteAUser.DeleteUserCommand
import com.archeon.daysoff.business.useCase.user.listObfuscatedUsers.ListAllObfuscatedUsers
import com.archeon.daysoff.business.useCase.user.listObfuscatedUsers.ListObfuscatedUsersCommand
import com.archeon.daysoff.business.useCase.user.listUsers.ListAllUsers
import com.archeon.daysoff.business.useCase.user.listUsers.ListAllUsersCommand
import com.archeon.daysoff.business.useCase.user.updateAUser.UpdateAUser
import com.archeon.daysoff.business.useCase.user.updateAUser.UpdateUserCommand
import com.archeon.daysoff.business.util.ErrorUtil.UNKNOWN_TEAM
import com.archeon.daysoff.business.util.ErrorUtil.USER_ALREADY_EXISTS
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import java.util.function.Consumer
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class UserSteps(
	userRepository: UserRepository,
	teamRepository: TeamRepository,
	sharedData: SharedData,
	createAUser: CreateAUser,
	updateAUser: UpdateAUser,
	deleteAUser: DeleteAUser,
	listAllUsers: ListAllUsers,
	listAllObfuscatedUsers: ListAllObfuscatedUsers,
	authenticationGateway: AuthenticationGateway,
	userService: UserService
) : En {

	init {
		Given("^des utilisateurs existent$") { dataTable: DataTable ->
			val dataMaps: List<Map<String, String>> = dataTable.asMaps(String::class.java, String::class.java)
			dataMaps.forEach(Consumer { dataMap: Map<String, String> ->
				val teamIds: MutableSet<String> = mutableSetOf()
				dataMap["services"]?.split(",")?.forEach { teamName ->
					teamIds.add(teamRepository.findByNameAndRemoved(teamName.trim(), false)?.id ?: error(UNKNOWN_TEAM))
				}

				val user = User(
					firstname = dataMap["prenom"] ?: error("no surname"),
					lastname = dataMap["nom"] ?: error("no lastname"),
					teamIds = teamIds,
					hasMinorChild = dataMap["avecOuSansEnfantMineur"] == WITH,
					role = dataMap["role"]?.let { Role.valueOf(it) } ?: error("bad role"),
					email = dataMap["email"] ?: error("no email"),
					credential = "password"
				)
				userService.updateUser(user)
			})
		}

		Given("^je suis connecté en tant qu'ADMINISTRATEUR$") {
			authenticationGateway.authenticate(mapOf(Pair("role", Role.ADMINISTRATOR.toString())))
			sharedData.currentUser = authenticationGateway.getAuthenticatedUser()
		}

		Given("^je suis connecté en tant que \"([^\"]*)\" \"([^\"]*)\"$") { lastname: String, firstname: String ->
			authenticationGateway.authenticate(
				mapOf(
					Pair("lastname", lastname),
					Pair("firstname", firstname),
				)
			)
			sharedData.currentUser = authenticationGateway.getAuthenticatedUser()
		}

		When("^je crée un USAGER \"([^\"]*)\" \"([^\"]*)\" dans le service \"([^\"]*)\", \"(\\w*)\" enfant mineur \"([^\"]*)\"$") { lastname: String, firstname: String, services: String, hasMinorChild: String, email: String ->
			val teamIds = services.split(",").map { team ->
				teamRepository.findByNameAndRemoved(
					team.trim(), false
				)?.id ?: ""
			}.toSet()

			val command = CreateUserCommand(
				lastname = lastname,
				firstname = firstname,
				hasMinorChild = hasMinorChild == WITH,
				teamIds = teamIds,
				email = email,
				credential = "password",
				role = Role.USER
			)

			try {
				createAUser.handle(command)
			} catch (e: Exception) {
				sharedData.currentException = e
			}
		}

		Then("^l'usager \"([^\"]*)\" \"([^\"]*)\" dans le service \"([^\"]*)\", \"(\\w*)\"  enfant mineur \"([^\"]*)\", existe dans l'application$") { lastname: String, firstname: String, services: String, hasMinorChild: String, email: String ->
			assertNotNull(userRepository.findAll().find { user ->
				user.lastname == lastname
						&& !user.isRemoved()
						&& user.firstname == firstname
						&& user.teamIds.size == services.split(",").toSet().size
						&& user.hasMinorChild == (hasMinorChild == WITH)
						&& user.email == email
						&& user.teamIds.containsAll(
					services.split(",")
						.map { service ->
							teamRepository.findByNameAndRemoved(
								service.trim(), false
							)?.id ?: ""
						})
			})
		}

		Then("Une erreur signalant que l'usager existe déjà est produite") {
			assertEquals(USER_ALREADY_EXISTS, sharedData.currentException?.message)
		}

		And("^l'usager \"([^\"]*)\" \"([^\"]*)\" n'existe qu'une fois dans l'application$") { lastname: String, firstname: String ->
			assertEquals(
				1,
				userRepository.findAll().filter {
					it.firstname == firstname
							&& it.lastname == lastname && !it.isRemoved()
				}.size
			)
		}

		When("^je modifie un USAGER \"([^\"]*)\" \"([^\"]*)\" avec les informations \"([^\"]*)\" \"([^\"]*)\" dans le service \"([^\"]*)\", \"([^\"]*)\" enfant mineur \"([^\"]*)\"$") { lastname: String, firstname: String, newLastname: String, newFirstname: String, newServices: String, newHasMinorChild: String, newEmail: String ->
			// get id from DB
			val currentUser = userRepository.findAll().first { it.lastname == lastname && it.firstname == firstname }
			val teamIds =
				newServices.split(",").map { team ->
					teamRepository.findByNameAndRemoved(
						team.trim(), false
					)?.id ?: ""
				}
					.toSet()

			val command = UpdateUserCommand(
				id = currentUser.id.toString(),
				lastname = newLastname,
				firstname = newFirstname,
				hasMinorChild = newHasMinorChild == WITH,
				teamIds = teamIds,
				email = newEmail,
				credential = "password"
			)

			try {
				updateAUser.handle(command)
			} catch (e: Exception) {
				sharedData.currentException = e
			}
		}

		When("^je supprime un USAGER \"([^\"]*)\" \"([^\"]*)\"$") { lastname: String, firstname: String ->
			// get id from DB
			val currentUser = userRepository.findAll().first { it.lastname == lastname && it.firstname == firstname }

			val command = DeleteUserCommand(
				id = currentUser.id.toString()
			)

			try {
				deleteAUser.handle(command)
			} catch (e: Exception) {
				sharedData.currentException = e
			}
		}

		Then("^l'usager \"([^\"]*)\" \"([^\"]*)\" n'existe plus dans l'application$") { lastname: String, firstname: String ->
			assertTrue {
				userRepository.findAll()
					.none { it.firstname == firstname && it.lastname == lastname && !it.isRemoved() }
			}
		}

		Given("je suis connecté en tant qu'USAGER") {
			authenticationGateway.authenticate(mapOf(Pair("role", Role.USER.toString())))
			sharedData.currentUser = authenticationGateway.getAuthenticatedUser()
		}

		Given("je suis connecté en tant que MANAGER") {
			authenticationGateway.authenticate(mapOf(Pair("role", Role.TEAM_MANAGER.toString())))
			sharedData.currentUser = authenticationGateway.getAuthenticatedUser()
		}

		When("^je liste les usagers du service '([^']*)'$") { service: String ->
			val command = ListAllUsersCommand()

			try {
				sharedData.domainDatas = listAllUsers.handle(command).toList()
			} catch (e: IllegalArgumentException) {
				sharedData.currentException = e
			}
		}

		When("^je liste tous les usagers avec toutes leurs informations$") {
			val command = ListAllUsersCommand()

			try {
				sharedData.domainDatas = listAllUsers.handle(command).toList()
			} catch (e: Exception) {
				sharedData.currentException = e
			}
		}

		Then("^j'obtiens les usagers suivant: '([^']*)'$") { names: String ->
			val expectedNames = names.split(",").map { it.trim() }.toSet()
			val loadedNames = sharedData.domainDatas.map { it as User }.map { it.lastname }
			assertEquals(expectedNames.size, loadedNames.size)
			assertTrue(expectedNames.containsAll(loadedNames))
		}

		When("^je liste tous les usagers avec des informations contrôlées$") {
			val command = ListObfuscatedUsersCommand()

			try {
				sharedData.domainDatas = listAllObfuscatedUsers.handle(command).toList()
			} catch (e: IllegalArgumentException) {
				sharedData.currentException = e
			}
		}

		@Suppress("UNCHECKED_CAST")
		Then("^j'obtiens tous les utilisateurs des autres équipe avec leurs informations restreintes$") {
			val users = sharedData.domainDatas as List<ObfuscatedUser>
			val loadedUsersInMyTeams = users.filter { user ->
				((user.teamIds?.intersect(
					sharedData.currentUser?.teamIds ?: emptyList()
				)?.size ?: 0) == 0
						&& user.email.isNullOrBlank())
			}

			val dbUsersInMyTeams = userRepository.findAll().filter { user ->
				((user.teamIds.intersect(
					sharedData.currentUser?.teamIds ?: emptyList()
				).size) == 0)
			}

			assertEquals(
				dbUsersInMyTeams.size,
				loadedUsersInMyTeams.size
			)
		}

		@Suppress("UNCHECKED_CAST")
		Then("^j'obtiens tous les utilisateurs de mes équipes avec leurs informations complètes$") {
			val users = sharedData.domainDatas as List<ObfuscatedUser>
			val loadedUsersInMyTeams = users.filter { user ->
				((user.teamIds?.intersect(
					sharedData.currentUser?.teamIds ?: emptyList()
				)?.size ?: 0) > 0
						&& (user.email?.isNotBlank() ?: false))
			}

			val dbUsersInMyTeams = userRepository.findAll().filter { user ->
				((user.teamIds.intersect(
					sharedData.currentUser?.teamIds ?: emptyList()
				).size) > 0)
			}

			assertEquals(
				dbUsersInMyTeams.size,
				loadedUsersInMyTeams.size
			)
		}

		@Suppress("UNCHECKED_CAST")
		Then("^j'obtiens tous les utilisateurs avec leurs informations complètes$") {
			val users = sharedData.domainDatas as List<User>
			assertEquals(
				userRepository.findAll().size,
				users.count { user ->
					user.email.isNotBlank()
				}
			)
		}

		@Suppress("UNCHECKED_CAST")
		Then("^j'obtiens tous les autres utilisateurs avec leurs informations restreintes$") {
			val users = sharedData.domainDatas as List<ObfuscatedUser>
			assertEquals(
				userRepository.findAll().filter { it.id != sharedData.currentUser?.id }.size,
				users.filter { it.id != sharedData.currentUser?.id }.count { user -> user.email.isNullOrBlank() }
			)
		}

		@Suppress("UNCHECKED_CAST")
		Then("^j'obtiens mes informations complètes$") {
			val users = sharedData.domainDatas as List<ObfuscatedUser>
			val loadedUser = users.first { it.id == sharedData.currentUser?.id }

			assertEquals(
				loadedUser.email,
				sharedData.currentUser?.email
			)
			assertEquals(
				loadedUser.teamIds,
				sharedData.currentUser?.teamIds
			)
			assertEquals(
				loadedUser.role,
				sharedData.currentUser?.role
			)
		}
	}

	companion object {
		private const val WITH = "avec"
	}
}
