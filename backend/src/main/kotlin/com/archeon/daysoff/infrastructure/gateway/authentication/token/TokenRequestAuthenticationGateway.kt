package com.archeon.daysoff.infrastructure.gateway.authentication.token

import com.archeon.daysoff.business.domain.user.User
import com.archeon.daysoff.business.port.gateway.AuthenticationGateway
import com.archeon.daysoff.business.port.generator.IdGenerator
import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.port.service.CryptService
import com.archeon.daysoff.business.port.service.HashService
import com.archeon.daysoff.business.util.ErrorUtil.BAD_AUTHENTICATION_REQUEST
import com.archeon.daysoff.business.util.ErrorUtil.INVALID_TOKEN
import com.archeon.daysoff.business.util.ErrorUtil.UNKNOWN_USER
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.servlet.http.HttpServletRequest

@Component
class TokenRequestAuthenticationGateway(
	private val userRepository: UserRepository,
	private val idGenerator: IdGenerator,
	private val cryptService: CryptService,
	private val hashService: HashService,
	@Suppress("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	@Lazy
	private val request: HttpServletRequest
) : AuthenticationGateway {

	companion object {
		const val TOKEN_LIFETIME_IN_MINUTES = 360
		const val TOKEN_MAX_INACTIVITY_IN_MINUTES = 60
		const val TOKEN = "token"
	}

	val tokens: MutableMap<String, Token> = mutableMapOf()

	val objectMapper: ObjectMapper = jacksonObjectMapper().registerKotlinModule().registerModule(JavaTimeModule())

	override fun getAuthenticatedUser(): User {
		try {
			val tokenString = cryptService.decrypt(request.getHeader(TOKEN))
			val tokenFromJson: Token = objectMapper.readValue(tokenString)
			val token = tokens[tokenFromJson.id] ?: throw IllegalAccessException(INVALID_TOKEN)
			val now = LocalDateTime.now()

			// check token lifetime
			require(ChronoUnit.MINUTES.between(token.nonce, now) <= TOKEN_LIFETIME_IN_MINUTES)
			require(ChronoUnit.MINUTES.between(token.lastTimeUsed, now) <= TOKEN_MAX_INACTIVITY_IN_MINUTES)

			// check token
			val user = userRepository.findById(token.userId) ?: throw IllegalArgumentException(UNKNOWN_USER)
			val builtTokenValue = buildTokenValue(user, token.nonce ?: error(INVALID_TOKEN))
			require(builtTokenValue == token.value)

			// update last time used on token
			token.lastTimeUsed = now
			tokens[token.id] = token

			return user
		} catch (exception: Exception) {
			throw IllegalAccessException(INVALID_TOKEN)
		}
	}

	override fun authenticate(authenticationData: Map<String, String>): String {
		val email = authenticationData["login"] ?: throw IllegalArgumentException(BAD_AUTHENTICATION_REQUEST)
		val credential = authenticationData["credential"] ?: throw IllegalArgumentException(BAD_AUTHENTICATION_REQUEST)
		val user = userRepository.findByEmailAndCredential(email, hashService.hash(credential)) ?: throw IllegalArgumentException(UNKNOWN_USER)

		// clean all token with this user
		tokens.values.filter { it.userId == user.id }.forEach { tokens.remove(it.id) }

		val tokenId = idGenerator.generate()
		val nonce = LocalDateTime.now()
		val token = Token(
			id = tokenId,
			nonce = nonce,
			value = buildTokenValue(user, nonce),
			lastTimeUsed = nonce,
			lifetime = TOKEN_LIFETIME_IN_MINUTES * 60,
			inactivityTime = TOKEN_MAX_INACTIVITY_IN_MINUTES * 60,
			userId = user.id ?: throw IllegalArgumentException(UNKNOWN_USER),
			userRole = user.role.toString()
		)

		tokens[tokenId] = token
		return cryptService.encrypt(objectMapper.writeValueAsString(token))
	}

	fun buildTokenValue(user: User, nonce: LocalDateTime): String {
		return hashService.hash(nonce.toString() + user.credential)
	}
}
