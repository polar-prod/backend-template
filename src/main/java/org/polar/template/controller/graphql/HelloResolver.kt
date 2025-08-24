package org.polar.template.controller.graphql

import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class HelloResolver {

    @QueryMapping
    fun hello(): String {
        return "Hello, Polar Productions!"
    }
}