package org.polar.template.controller

import org.polar.template.service.AuthService
import org.springframework.http.ResponseEntity
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
    suspend fun getGoogleUrl(@RequestParam redirect: String): ResponseEntity<String> {
        val url = authService.googleAuthUrl(redirect)
        return ResponseEntity.ok(url)
    }

    @GetMapping("/github")
    suspend fun getGithubUrl(@RequestParam redirect: String): ResponseEntity<String> {
        val url = authService.gitHubAuthUrl(redirect)
        return ResponseEntity.ok(url)
    }

    @GetMapping("/apple")
    suspend fun getAppleUrl(@RequestParam redirect: String): ResponseEntity<String> {
        val url = authService.appleAuthUrl(redirect)
        return ResponseEntity.ok(url)
    }
}