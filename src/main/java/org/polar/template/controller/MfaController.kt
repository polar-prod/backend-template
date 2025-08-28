package org.polar.template.controller

import org.polar.template.service.MfaService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/api/mfa")
class MfaController(private val mfaService: MfaService) {

    @PostMapping("/enroll")
    suspend fun enroll(@RequestHeader("Authorization") authHeader: String): ResponseEntity<Any> {
        val token = extractBearerToken(authHeader) ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        return try {
            val response = mfaService.enrollTotp(token)
            ResponseEntity.ok(response)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapOf("error" to e.message))
        }
    }

    @PostMapping("/challenge")
    suspend fun challenge(
        @RequestHeader("Authorization") authHeader: String,
        @RequestParam factorId: String
    ): ResponseEntity<Any> {
        val token = extractBearerToken(authHeader) ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        return try {
            val challengeId = mfaService.createChallenge(token, factorId)
            ResponseEntity.ok(mapOf("challengeId" to challengeId))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapOf("error" to e.message))
        }
    }

    @PostMapping("/verify")
    suspend fun verify(
        @RequestHeader("Authorization") authHeader: String,
        @RequestParam factorId: String,
        @RequestParam challengeId: String,
        @RequestParam code: String
    ): ResponseEntity<Any> {
        val token = extractBearerToken(authHeader) ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        return try {
            val success = mfaService.verifyChallenge(token, factorId, challengeId, code)
            if (success) ResponseEntity.ok(mapOf("message" to "MFA verified"))
            else ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("error" to "Invalid MFA code"))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapOf("error" to e.message))
        }
    }

    @GetMapping("/assurance-level")
    suspend fun assuranceLevel(@RequestHeader("Authorization") authHeader: String): ResponseEntity<Any> {
        val token = extractBearerToken(authHeader) ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        return try {
            val response = mfaService.getAuthenticatorAssuranceLevel(token)
            ResponseEntity.ok(response)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapOf("error" to e.message))
        }
    }

    private fun extractBearerToken(authHeader: String?): String? {
        if (authHeader.isNullOrBlank() || !authHeader.startsWith("Bearer ")) return null
        return authHeader.removePrefix("Bearer ").trim()
    }
}