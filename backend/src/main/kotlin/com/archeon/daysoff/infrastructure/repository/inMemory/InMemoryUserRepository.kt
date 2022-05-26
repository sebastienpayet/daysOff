package com.archeon.daysoff.infrastructure.repository.inMemory

import com.archeon.daysoff.business.domain.user.User
import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.util.ErrorUtil.UNKNOWN_USER

class InMemoryUserRepository : UserRepository {
	private val users: LinkedHashMap<String, User> = LinkedHashMap()

	override fun findByFirstnameAndLastname(firstname: String, lastname: String): User {
		return users.values.find { it.lastname == lastname && it.firstname == firstname } ?: error(UNKNOWN_USER)
	}

	override fun findAllByTeamIdsIn(ids: Set<String>): Set<User> {
		return users.values.filter { it.teamIds.intersect(ids).isNotEmpty() }.toSet()
	}

	override fun findByEmailAndCredential(email: String, credential: String): User? {
		return users.values.find { it.email == email && it.credential == credential }
	}

	override fun findByEmail(email: String): User? {
		return users.values.find { it.email == email.trim().lowercase() }
	}

	override fun save(domain: User): User {
		users[domain.id.toString()] = domain
		return domain
	}

	override fun findAll(): List<User> {
		return users.values.toList()
	}

	override fun hardDeleteAll() {
		users.clear()
	}

	override fun findById(id: String): User {
		return users[id] ?: error(UNKNOWN_USER)
	}
}
