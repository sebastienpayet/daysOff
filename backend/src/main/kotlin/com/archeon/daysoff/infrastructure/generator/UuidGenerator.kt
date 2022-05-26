package com.archeon.daysoff.infrastructure.generator

import com.archeon.daysoff.business.port.generator.IdGenerator
import java.util.*

class UuidGenerator : IdGenerator {
    override fun generate(): String {
        return UUID.randomUUID().toString()
    }
}
