package com.archeon.daysoff.business.useCase

interface UseCase<COMMAND: Command, DOMAIN> {
    fun handle(command: COMMAND): DOMAIN
}
