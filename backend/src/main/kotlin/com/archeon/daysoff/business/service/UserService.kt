package com.archeon.daysoff.business.service

import com.archeon.daysoff.business.domain.user.ObfuscatedUser
import com.archeon.daysoff.business.domain.user.Role
import com.archeon.daysoff.business.domain.user.User
import com.archeon.daysoff.business.port.gateway.AuthenticationGateway
import com.archeon.daysoff.business.port.generator.IdGenerator
import com.archeon.daysoff.business.port.repository.TeamRepository
import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.port.service.HashService
import com.archeon.daysoff.business.service.TeamService.Companion.DEFAULT_TEAM
import com.archeon.daysoff.business.util.ErrorUtil
import java.time.Instant
import java.time.temporal.ChronoUnit

class UserService(
	private val userRepository: UserRepository,
	private val idGenerator: IdGenerator,
	private val hashService: HashService,
	private val authenticationGateway: AuthenticationGateway,
	private val teamRepository: TeamRepository
) {

	fun initData() {
		if (userRepository.findAll().isEmpty()) {

			val managementService =
				teamRepository.findByNameAndRemoved(DEFAULT_TEAM, false) ?: throw IllegalArgumentException(ErrorUtil.UNKNOWN_TEAM)

			val adminId = idGenerator.generate()
			val savingDate = Instant.now().truncatedTo(ChronoUnit.SECONDS)
			val user = User(
				id = adminId,
				createdBy = adminId,
				createdDate = savingDate,
				lastModifiedBy = adminId,
				lastModifiedDate = savingDate,
				firstname = "admin",
				lastname = "admin",
				hasMinorChild = false,
				teamIds = setOf(managementService.id ?: ""),
				role = Role.ADMINISTRATOR,
				email = "dumb@email.com",
				credential = hashService.hash("dumb@email.com")
			)
			userRepository.save(user)
		}
	}

	fun updateUser(user: User): User {

		val currentId = user.id ?: idGenerator.generate()
		val createdDate = user.createdDate ?: Instant.now()
		val currentUserId = authenticationGateway.getAuthenticatedUser().id.toString()
		val createdBy = user.createdBy ?: currentUserId

		val userToSave = User(
			lastname = user.lastname,
			firstname = user.firstname,
			hasMinorChild = user.hasMinorChild,
			role = user.role,
			teamIds = user.teamIds,
			email = user.email,
			credential = user.credential,
			id = currentId,
			createdDate = createdDate,
			createdBy = createdBy,
			lastModifiedBy = currentUserId,
			lastModifiedDate = Instant.now(),
			removed = user.isRemoved(),
			version = user.version
		)

		return userRepository.save(userToSave)
	}

	fun listAllUsers(): Set<User> {
		return userRepository.findAll().toSet()
	}

	fun securedListAllUsers(): Set<ObfuscatedUser> {
		val user = authenticationGateway.getAuthenticatedUser()
		val allUsers = userRepository.findAll()
		return when (user.role) {
			Role.USER -> {
				allUsers.map {
					if (it.id == user.id) { // full user info
						buildFullInfoObfuscatedUser(it)
					} else {
						buildObfuscatedUser(it)
					}
				}.toSet()
			}
			Role.TEAM_MANAGER -> {
				// just users of managed services
				allUsers.map {
					if (user.teamIds.intersect(it.teamIds).isNotEmpty()) { // full user info
						buildFullInfoObfuscatedUser(it)
					} else {
						buildObfuscatedUser(it)
					}
				}.toSet()

			}
			Role.ADMINISTRATOR -> {
				allUsers.map { buildFullInfoObfuscatedUser(it) }.toSet()
			}
		}
	}

	private fun buildObfuscatedUser(user: User): ObfuscatedUser {
		return ObfuscatedUser(
			firstname = user.firstname,
			lastname = user.lastname,
			id = user.id
		)
	}

	private fun buildFullInfoObfuscatedUser(user: User): ObfuscatedUser {
		return ObfuscatedUser(
			firstname = user.firstname,
			lastname = user.lastname,
			hasMinorChild = user.hasMinorChild,
			teamIds = user.teamIds,
			role = user.role,
			email = user.email,
			id = user.id
		)
	}
}
