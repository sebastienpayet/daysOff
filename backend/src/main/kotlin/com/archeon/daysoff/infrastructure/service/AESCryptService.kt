package com.archeon.daysoff.infrastructure.service

import com.archeon.daysoff.business.port.service.CryptService
import java.time.Clock
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

const val aes = "AES"

class AESCryptService(
    private val clock: Clock
) : CryptService {
    private var key: ByteArray = byteArrayOf()
    private var cipher: Cipher = Cipher.getInstance(aes)

    private fun initKey() {
        val today = LocalDate.ofInstant(clock.instant(), ZoneOffset.systemDefault())
        val checkedKey = today.toString().replace("-",today.dayOfYear.toString()).reversed().padEnd(16, today.dayOfMonth.toString().elementAt(0)).substring(0,16)
        key = checkedKey.toByteArray()
    }

    private fun initEncryptMode() {
        initKey()
        cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(this.key, aes))
    }

    private fun initDecryptMode() {
        initKey()
        cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(this.key, aes))
    }

    override fun encrypt(data: String): String {
        initEncryptMode()
        val byteData: ByteArray = data.toByteArray()
        val encryptedValue: ByteArray = cipher.doFinal(byteData)
        return Base64.getEncoder().encodeToString(encryptedValue)
    }

    override fun decrypt(encryptedData: String): String {
        initDecryptMode()
        val byteEncryptedData: ByteArray = Base64.getDecoder().decode(encryptedData.toByteArray())
        return String(cipher.doFinal(byteEncryptedData))
    }

    override fun getKey(): String {
        return Base64.getEncoder().encodeToString(key)
    }
}