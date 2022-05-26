package com.archeon.daysoff.application.configuration.adapter.converter

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("mongo")
@ComponentScan(basePackages = ["com.archeon.daysoff.infrastructure.converter.mongoDb"])
class MongoDataConverterConfiguration
