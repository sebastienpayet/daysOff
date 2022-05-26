package com.archeon.daysoff.business.useCase.user.deleteAUser

import com.archeon.daysoff.business.useCase.Command

class DeleteUserCommand(
    val id: String
    ): Command {
        init {
            require(id.isNotEmpty())
        }
    }
