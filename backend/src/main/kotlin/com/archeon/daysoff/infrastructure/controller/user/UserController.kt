package com.archeon.daysoff.infrastructure.controller.user

import com.archeon.daysoff.business.port.service.CryptService
import com.archeon.daysoff.business.useCase.user.authenticate.LoginCommand
import com.archeon.daysoff.business.useCase.user.createAUser.CreateAUser
import com.archeon.daysoff.business.useCase.user.createAUser.CreateUserCommand
import com.archeon.daysoff.business.useCase.user.listObfuscatedUsers.ListAllObfuscatedUsers
import com.archeon.daysoff.business.useCase.user.listObfuscatedUsers.ListObfuscatedUsersCommand
import com.archeon.daysoff.business.useCase.user.listUsers.ListAllUsers
import com.archeon.daysoff.business.useCase.user.listUsers.ListAllUsersCommand
import com.archeon.daysoff.business.useCase.user.updateAUser.UpdateAUser
import com.archeon.daysoff.business.useCase.user.updateAUser.UpdateUserCommand
import com.archeon.daysoff.infrastructure.converter.output.user.ObfuscatedUserOutputConverter
import com.archeon.daysoff.infrastructure.converter.output.user.UserOutputConverter
import com.archeon.daysoff.infrastructure.gateway.authentication.token.TokenRequestAuthenticationGateway
import com.archeon.daysoff.infrastructure.resource.output.user.UserOutput
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("user")
class UserController(
	private val createAUser: CreateAUser,
	private val updateAUser: UpdateAUser,
	private val listAllUsers: ListAllUsers,
	private val listAllObfuscatedUsers: ListAllObfuscatedUsers,
	private val userOutputConverter: UserOutputConverter,
	private val obfuscatedUserOutputConverter: ObfuscatedUserOutputConverter,
	private val authenticationGateway: TokenRequestAuthenticationGateway,
	private val cryptService: CryptService
) : UserApi {

	@GetMapping("listAllUsers")
	override fun listAllUsers(): List<UserOutput> {
		return listAllUsers.handle(ListAllUsersCommand()).map { userOutputConverter.toResource(it) }.toList()
	}

	@GetMapping("handshake")
	fun handshake(): String {
		return "hello"
	}

	@GetMapping("listAllObfuscatedUsers")
	override fun listAllObfuscatedUsers(): List<UserOutput> {
		return listAllObfuscatedUsers.handle(ListObfuscatedUsersCommand())
			.map { obfuscatedUserOutputConverter.toResource(it) }.toList()
	}

	@PostMapping
	override fun createAUser(@RequestBody createUserCommand: CreateUserCommand): UserOutput {
		return userOutputConverter.toResource(createAUser.handle(createUserCommand))
	}

	@PostMapping("login", produces = ["text/plain"])
	override fun login(@RequestBody loginCommand: LoginCommand, response: HttpServletResponse): String {
		val data: Map<String, String> = mapOf("login" to loginCommand.login, "credential" to loginCommand.credential)
		val result = authenticationGateway.authenticate(data)
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PATCH")
		response.addHeader(
			"Access-Control-Allow-Headers",
			"Origin, X-Requested-With, Content-Type, Accept, Authorization, k"
		)
		response.addHeader("Access-Control-Expose-Headers", "k")
		response.addHeader("k", cryptService.getKey())
		return result
	}

	@PatchMapping
	override fun updateAUser(@RequestBody updateUserCommand: UpdateUserCommand): UserOutput {
		return userOutputConverter.toResource(updateAUser.handle(updateUserCommand))
	}
}
