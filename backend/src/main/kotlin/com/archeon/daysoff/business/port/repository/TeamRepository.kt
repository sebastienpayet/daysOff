package com.archeon.daysoff.business.port.repository

import com.archeon.daysoff.business.domain.team.Team

interface TeamRepository : Repository<Team> {
	fun findByNameAndRemoved(name: String, removed: Boolean): Team?
}
