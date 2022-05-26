package com.archeon.daysoff.business.port.service

interface CryptService {
    fun encrypt(data: String): String
    fun decrypt(encryptedData: String): String
    fun getKey(): String
}