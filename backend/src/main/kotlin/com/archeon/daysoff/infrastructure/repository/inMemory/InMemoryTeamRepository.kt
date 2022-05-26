package com.archeon.daysoff.infrastructure.repository.inMemory

import com.archeon.daysoff.business.domain.team.Team
import com.archeon.daysoff.business.port.repository.TeamRepository

class InMemoryTeamRepository : TeamRepository {
	private val teams: LinkedHashMap<String, Team> = LinkedHashMap()
	override fun findByNameAndRemoved(name: String, removed: Boolean): Team? {
		return teams.values.firstOrNull { it.name == name }
	}

	override fun save(domain: Team): Team {
		teams[domain.id.toString()] = domain
		return domain
	}

	override fun findAll(): List<Team> {
		return teams.values.filter { !it.isRemoved() }.toList()
	}

	override fun hardDeleteAll() {
		teams.clear()
	}

	override fun findById(id: String): Team? {
		return teams[id]
	}
}
