package com.archeon.daysoff.infrastructure.service

import org.junit.Test

import org.junit.Assert.*

class HmacSHA256HashServiceTest {

	val service = HmacSHA256HashService()

	@Test
	fun shouldHash() {
		val result = service.hash("Ceci est mon test")
		assertEquals("5368378e0aaa24f2d7fe89bfa508f3cc5405ffed80fe1ac7314ca2671f0d0392", result)
	}

}