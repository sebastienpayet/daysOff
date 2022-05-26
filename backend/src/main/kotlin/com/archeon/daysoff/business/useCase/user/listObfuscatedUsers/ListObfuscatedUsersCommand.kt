package com.archeon.daysoff.business.useCase.user.listObfuscatedUsers

import com.archeon.daysoff.business.useCase.Command

class ListObfuscatedUsersCommand(val teamIds: Set<String> = emptySet()) : Command
