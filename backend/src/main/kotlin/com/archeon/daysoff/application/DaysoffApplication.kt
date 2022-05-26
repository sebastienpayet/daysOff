package com.archeon.daysoff.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.archeon.daysoff.application"])
class DaysoffApplication

fun main(args: Array<String>) {
    runApplication<DaysoffApplication>(*args)
}
