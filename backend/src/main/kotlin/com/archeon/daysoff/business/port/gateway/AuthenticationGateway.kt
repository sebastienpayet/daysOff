package com.archeon.daysoff.business.port.gateway

import com.archeon.daysoff.business.domain.user.User

interface AuthenticationGateway {
    fun getAuthenticatedUser(): User
    fun authenticate(authenticationData:Map<String, String>): String
}
