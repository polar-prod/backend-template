package org.polar.template.service

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.mfa.FactorType
import org.polar.template.model.AuthenticatorAssuranceLevelResponse
import org.polar.template.model.MfaEnrollResponse
import org.springframework.stereotype.Service

@Service
class MfaService(
    private val authService: AuthService,
    private val supabaseClient: SupabaseClient
) {

    suspend fun enrollTotp(authToken: String, friendlyName: String = "Authenticator App"): MfaEnrollResponse {
        this.authService.importSessionFromFrontend(authToken)

        val factorResult = this.supabaseClient.auth.mfa.enroll(
            factorType = FactorType.TOTP,
            friendlyName = friendlyName
        )

        val factor = factorResult.data as FactorType.TOTP.Response
        return MfaEnrollResponse(
            factorId = factorResult.id,
            qrCodeSvg = factor.qrCode
        )
    }

    suspend fun createChallenge(authToken: String, factorId: String): String {
        this.authService.importSessionFromFrontend(authToken)
        val challengeResult = this.supabaseClient.auth.mfa.createChallenge(factorId)
        return challengeResult.id
    }

    suspend fun verifyChallenge(authToken: String, factorId: String, challengeId: String, code: String): Boolean {
        this.authService.importSessionFromFrontend(authToken)

        return try {
            this.supabaseClient.auth.mfa.verifyChallenge(
                factorId = factorId,
                challengeId = challengeId,
                code = code,
                saveSession = true
            )
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getAuthenticatorAssuranceLevel(authToken: String): AuthenticatorAssuranceLevelResponse {
        this.authService.importSessionFromFrontend(authToken)
        val (current, next) = this.supabaseClient.auth.mfa.getAuthenticatorAssuranceLevel()
        return AuthenticatorAssuranceLevelResponse(currentLevel = current, nextLevel = next)
    }
}