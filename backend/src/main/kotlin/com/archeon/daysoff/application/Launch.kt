package com.archeon.daysoff.application

import com.archeon.daysoff.business.service.SettingsService
import com.archeon.daysoff.business.service.TeamService
import com.archeon.daysoff.business.service.UserService
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component


@Component
class Launch(
	private val userService: UserService,
	private val teamService: TeamService,
	private val settingsService: SettingsService
) {
	@EventListener(ApplicationStartedEvent::class)
	fun launchApp() {
		teamService.initData()
		userService.initData()
		settingsService.initSettings()
	}
}
