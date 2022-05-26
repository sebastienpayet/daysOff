package com.archeon.daysoff.business.port.converter

interface ResourceConverter<DOMAIN, RESOURCE> {
    fun toResource(domain: DOMAIN): RESOURCE
    fun toDomain(resource: RESOURCE): DOMAIN
}
