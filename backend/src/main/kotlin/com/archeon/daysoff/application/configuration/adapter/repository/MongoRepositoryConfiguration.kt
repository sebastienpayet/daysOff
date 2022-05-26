package com.archeon.daysoff.application.configuration.adapter.repository

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Profile("mongo")
@Configuration
@EnableMongoRepositories(basePackages = ["com.archeon.daysoff.infrastructure.repository.mongoDb"])
@EnableMongoAuditing
@ComponentScan(basePackages = ["com.archeon.daysoff.infrastructure.repository.mongoDb"])
class MongoRepositoryConfiguration
