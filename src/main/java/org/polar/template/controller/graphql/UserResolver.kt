package org.polar.template.controller.graphql

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Controller

@Controller
class UserResolver(
    private val request: HttpServletRequest
) {

}