package com.archeon.daysoff.business.port.repository

import com.archeon.daysoff.business.domain.user.User

interface UserRepository : Repository<User> {
	fun findByFirstnameAndLastname(firstname: String, lastname: String): User?
	fun findAllByTeamIdsIn(ids: Set<String>): Set<User>
    fun findByEmailAndCredential(email: String, credential: String): User?
    fun findByEmail(email: String): User?
}
