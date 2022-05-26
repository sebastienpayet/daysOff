package com.archeon.daysoff.application

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(properties = ["spring.profiles.active:memory"])
class DaysOffApplicationInMemoryConfigurationTest {
    @Test
    fun applicationShouldStart() {
    }
}
