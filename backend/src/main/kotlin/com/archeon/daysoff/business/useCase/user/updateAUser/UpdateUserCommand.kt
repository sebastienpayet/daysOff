package com.archeon.daysoff.business.useCase.user.updateAUser

import com.archeon.daysoff.business.useCase.Command

class UpdateUserCommand(
    val id: String,
    val lastname: String,
    val firstname:String,
    val hasMinorChild: Boolean,
    val teamIds: Set<String>,
    val email: String,
    val credential: String?
    ): Command