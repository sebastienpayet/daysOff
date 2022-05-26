package com.archeon.daysoff.infrastructure.controller.user

import com.archeon.daysoff.business.useCase.user.createAUser.CreateUserCommand
import com.archeon.daysoff.business.useCase.user.authenticate.LoginCommand
import com.archeon.daysoff.business.useCase.user.updateAUser.UpdateUserCommand
import com.archeon.daysoff.infrastructure.resource.output.user.UserOutput
import javax.servlet.http.HttpServletResponse

interface UserApi {
	fun createAUser(createUserCommand: CreateUserCommand): UserOutput
	fun updateAUser(updateUserCommand: UpdateUserCommand): UserOutput
	fun login(loginCommand: LoginCommand, response: HttpServletResponse): String
	fun listAllUsers(): List<UserOutput>
	fun listAllObfuscatedUsers(): List<UserOutput>
}
