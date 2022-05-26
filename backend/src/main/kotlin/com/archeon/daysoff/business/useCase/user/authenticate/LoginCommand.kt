package com.archeon.daysoff.business.useCase.user.authenticate

import com.archeon.daysoff.business.useCase.Command

class LoginCommand(
    val login:String,
    val credential: String
): Command
