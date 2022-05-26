package com.archeon.daysoff.infrastructure.repository.mongoDb.settings

import com.archeon.daysoff.infrastructure.resource.mongoDb.settings.SettingsDocument
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Profile("mongo")
@Repository
interface NativeMongoSettingsRepository : MongoRepository<SettingsDocument, String>
