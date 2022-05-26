package com.archeon.daysoff.infrastructure.service

import com.archeon.daysoff.business.port.service.HashService
import org.apache.commons.codec.binary.Hex
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class HmacSHA256HashService : HashService {

	companion object {
		private const val HMAC_SHA256 = "HmacSHA256"
		private const val UTF_8 = "UTF-8"
	}

	private val logger: Logger = LoggerFactory.getLogger(HmacSHA256HashService::class.java)
	private val hashSecretKey: String = "thisIsMySecretPrivateKey3479"

	override fun hash(data: String): String {
		try {
			val hmacSha256 = Mac.getInstance(HMAC_SHA256)
			val secretKey = SecretKeySpec(
				hashSecretKey.toByteArray(charset(UTF_8)),
				HMAC_SHA256
			)
			hmacSha256.init(secretKey)
			val result: String =
				Hex.encodeHexString(hmacSha256.doFinal(data.toByteArray(charset(UTF_8))))
			logger.debug(result)
			return result
		} catch (e: Exception) {
			logger.error("Error on signature creation", e)
		}
		return "error"
	}

}
