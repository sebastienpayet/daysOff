package com.archeon.daysoff.application.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.header.writers.StaticHeadersWriter

@Configuration
@EnableWebSecurity
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

	override fun configure(http: HttpSecurity) {
		http
			.headers()
			.contentTypeOptions().and()
			.xssProtection().and()
			.cacheControl().and()
			.httpStrictTransportSecurity().and()
			.frameOptions().sameOrigin()
			.addHeaderWriter(StaticHeadersWriter("X-Content-Security-Policy",
				"default-src 'self'; " +
						"    img-src 'self'; " +
						"    base-uri 'self'; " +
						"    form-action 'none'; " +
						"    script-src 'self'; " +
						"    style-src 'unsafe-inline' 'self' fonts.googleapis.com; " +
						"    object-src 'none'; " +
						"    frame-ancestors 'none'; " +
						"    font-src 'self' fonts.gstatic.com fonts.googleapis.com"))
			.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
	}
}
