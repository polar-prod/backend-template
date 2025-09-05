package org.polar.template.controller

import jakarta.servlet.http.HttpServletRequest
import org.polar.template.model.UserResponse
import org.polar.template.service.AuthService
import org.polar.template.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/user")
class UserController(
    private val authService: AuthService,
    private val userService: UserService
) {

    private val logger = LoggerFactory.getLogger(UserController::class.java)

    @GetMapping("/@me")
    suspend fun getUserFromToken(request: HttpServletRequest): ResponseEntity<UserResponse> {

        if (request.cookies == null || request.cookies.isEmpty()) {
            logger.warn("Keine Cookies im Request gefunden.")
        } else {
            logger.info("Cookies im Request:") //TODO wird nicht geloggt idk sehr weird du hs
            request.cookies.forEach {
                logger.info("Cookie: ${it.name} = ${it.value}")
            }
        }

        val cookieName = "JSESSIONID"
        val token = request.cookies
            ?.firstOrNull { it.name == cookieName }
            ?.value

        if (token.isNullOrBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }

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