package com.archeon.daysoff.acceptance

import com.archeon.daysoff.business.domain.user.User
import com.archeon.daysoff.business.domain.Domain
import io.cucumber.spring.CucumberTestContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

class SharedData {
    var currentUser: User? = null
    var currentException: Exception? = null
    var domainDatas: List<Domain<*>> = emptyList()
}
