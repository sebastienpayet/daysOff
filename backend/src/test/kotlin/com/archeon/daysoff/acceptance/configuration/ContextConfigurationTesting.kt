package com.archeon.daysoff.acceptance.configuration

import io.cucumber.java8.En
import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.test.context.ContextConfiguration

@CucumberContextConfiguration
@ContextConfiguration(classes = [
	RepositoriesConfiguration::class,
	AuthorizationGatewayConfiguration::class,
	AuthenticationGatewayConfiguration::class,
	CommonConfiguration::class,
	UseCaseConfiguration::class
])
internal class ContextConfigurationTesting : En
