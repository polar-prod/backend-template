package org.polar.template.controller

import org.polar.template.model.UserResponse
import org.polar.template.service.AuthService
import org.polar.template.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
    suspend fun getUserFromToken(@RequestHeader("Authorization") authHeader: String): ResponseEntity<UserResponse> {
        if (authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }

        val token = authHeader.removePrefix("Bearer ").trim()

        return try {
            this.authService.importSessionFromFrontend(token)

            val user = this.userService.currentUser()
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
            }

            return ResponseEntity.ok(UserResponse(email = user.email))
        } catch (ex: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

}