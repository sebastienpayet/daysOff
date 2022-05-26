package com.archeon.daysoff.infrastructure.service

import org.junit.Test

import org.junit.Assert.*
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

class AESCryptServiceTest {

	val service = AESCryptService(Clock.fixed(Instant.ofEpochMilli(0), ZoneOffset.UTC))

	@Test
	fun shouldEncrypt() {
		val result = service.encrypt("Ceci est mon test")
		assertEquals("peum+1sCJ/JQCwH9cXOXNubLZjwAlTT+ANiDxdECoMs=", result)
	}

	@Test
	fun decrypt() {
		val result = service.decrypt("peum+1sCJ/JQCwH9cXOXNubLZjwAlTT+ANiDxdECoMs=")
		assertEquals("Ceci est mon test", result)
	}

	@Test
	fun shouldEncryptAndDecrypt() {
		val result = service.decrypt(service.encrypt("Ceci est mon test"))
		assertEquals("Ceci est mon test", result)
	}
}