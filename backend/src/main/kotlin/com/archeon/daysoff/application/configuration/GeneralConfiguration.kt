package com.archeon.daysoff.application.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@ComponentScan(basePackages = ["com.archeon.daysoff.infrastructure"])
class GeneralConfiguration {

	@Bean
	fun corsConfigurer(): WebMvcConfigurer? {
		return object : WebMvcConfigurer {
			override fun addCorsMappings(registry: CorsRegistry) {
				registry.addMapping("/**").allowedMethods("GET","POST","PATCH","DELETE","OPTIONS")
			}
		}
	}
}
