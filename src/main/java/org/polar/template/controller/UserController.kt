package org.polar.template.controller

import kotlinx.coroutines.runBlocking
import org.polar.template.service.AuthService
import org.polar.template.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/user")
class UserController(
    private val authService: AuthService,
    private val userService: UserService
) {

    @GetMapping("/@me")
    fun getUserFromToken(@RequestHeader("Authorization") authHeader: String): String? {
        val token = authHeader.removePrefix("Bearer ").trim()

        return runBlocking {
            authService.importSessionFromFrontend(token)

            val user = userService.currentUser()
            user?.email
        }
    }

}