package com.archeon.daysoff.business.domain

interface Domain<T> {
    override fun equals(other: Any?): Boolean
    override fun hashCode(): Int
    fun copyDomainData(source: T): T
    fun remove()
    fun unRemove()
    fun isRemoved(): Boolean
}
