package io.github.flowers.test_client

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TestClientApplication

fun main(args: Array<String>) {
	runApplication<TestClientApplication>(*args)
}
