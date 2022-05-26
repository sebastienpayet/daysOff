package com.archeon.daysoff.application.configuration.adapter.generator

import com.archeon.daysoff.business.port.generator.IdGenerator
import com.archeon.daysoff.infrastructure.generator.UuidGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GeneratorConfiguration {

    @Bean
    fun idGenerator(): IdGenerator {
        return UuidGenerator()
    }
}
