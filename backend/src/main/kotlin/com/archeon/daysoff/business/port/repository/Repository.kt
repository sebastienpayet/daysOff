package com.archeon.daysoff.business.port.repository

import com.archeon.daysoff.business.domain.Domain

interface Repository<T: Domain<*>> {
    fun save(domain: T): T
    fun findAll(): List<T>
    fun hardDeleteAll()
    fun findById(id: String): T?
}
