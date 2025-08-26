package org.polar.template.controller.graphql

import jakarta.servlet.http.HttpServletRequest
import org.polar.template.model.Role
import org.polar.template.model.User
import org.polar.template.service.AuthService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import java.util.UUID

@Controller
class UserResolver(
    private val authService: AuthService,
    private val request: HttpServletRequest
) {

}