package io.github.flowers.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AuthServerApp

fun main(args: Array<String>) {
	runApplication<AuthServerApp>(*args)
}
