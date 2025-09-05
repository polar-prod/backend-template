package org.polar.template.controller.graphql

import jakarta.servlet.http.HttpServletRequest
import org.polar.template.model.UserResponse
import org.polar.template.service.AuthService
import org.polar.template.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class UserResolver(
    private val request: HttpServletRequest,
    private val authService: AuthService,
    private val userService: UserService
) {

    private val logger = LoggerFactory.getLogger(UserResolver::class.java)

    suspend fun me(): UserResponse? {
        val cookieName = "JSESSIONID"
        val token = request.cookies
            ?.firstOrNull { it.name == cookieName }
            ?.value

        if (token.isNullOrBlank()) {
            logger.warn("Token (JSESSIONID) nicht vorhanden.")
            return null
        }

        return try {
            authService.importSessionFromFrontend(token)

            val user = userService.currentUser()
            user?.let {
                UserResponse(email = it.email)
            }
        } catch (ex: Exception) {
            logger.error("Fehler beim Authentifizieren", ex)
            null
        }
    }
}