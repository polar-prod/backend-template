package org.polar.template.controller

import kotlinx.coroutines.runBlocking
import org.polar.template.service.AuthService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    @GetMapping("/google")
    fun getGoogleUrl(@RequestParam redirect: String): String {
        return runBlocking {
            authService.googleAuthUrl(redirect)
        }
    }

    @GetMapping("/github")
    fun getGithubUrl(@RequestParam redirect: String): String {
        return runBlocking {
            authService.gitHubAuthUrl(redirect)
        }
    }

    @GetMapping("/github")
    fun getAppleUrl(@RequestParam redirect: String): String {
        return runBlocking {
            authService.appleAuthUrl(redirect)
        }
    }
}