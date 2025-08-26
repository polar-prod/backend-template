package org.polar.template

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SaasTemplate

fun main(args: Array<String>) {
    runApplication<SaasTemplate>(*args)
}