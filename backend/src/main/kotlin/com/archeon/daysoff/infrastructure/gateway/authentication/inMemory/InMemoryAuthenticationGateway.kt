package com.archeon.daysoff.infrastructure.gateway.authentication.inMemory

import com.archeon.daysoff.business.domain.user.Role
import com.archeon.daysoff.business.domain.user.User
import com.archeon.daysoff.business.port.gateway.AuthenticationGateway
import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.port.service.CryptService
import com.archeon.daysoff.business.util.ErrorUtil.BAD_AUTHENTICATION_REQUEST

// /!\ ONLY FOR IN MEMORY TESTING, DO NOT USE IN PRODUCTION /!\
// /!\ ONLY FOR IN MEMORY TESTING, DO NOT USE IN PRODUCTION /!\
// /!\ ONLY FOR IN MEMORY TESTING, DO NOT USE IN PRODUCTION /!\
class InMemoryAuthenticationGateway(
	private val userRepository: UserRepository,
	private val cryptService: CryptService
) : AuthenticationGateway {

	private var loggedUser: User? = null

	// for test, if no user is logged, take the first in DB (admin)
	override fun getAuthenticatedUser(): User {
		return loggedUser ?: userRepository.findAll().first()
	}

	override fun authenticate(authenticationData: Map<String, String>): String {
		if (authenticationData["role"] != null) {
			val targetRole = Role.valueOf(authenticationData["role"] ?: "USER")
			loggedUser = userRepository.findAll().firstOrNull { it.role == targetRole }
		} else if (authenticationData["lastname"] != null && authenticationData["firstname"] != null) {
			loggedUser = userRepository.findAll().first {
				it.firstname == authenticationData["firstname"]
						&& it.lastname == authenticationData["lastname"]
			}
		} else {
			error(BAD_AUTHENTICATION_REQUEST)
		}
		return "/!\\ ONLY FOR TEST /!\\ in memory log in OK /!\\"
	}
}
