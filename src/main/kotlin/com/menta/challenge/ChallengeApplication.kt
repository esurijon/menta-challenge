package com.menta.challenge

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
class ChallengeApplication

fun main(args: Array<String>) {
	runApplication<ChallengeApplication>(*args)
}
