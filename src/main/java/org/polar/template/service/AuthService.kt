package org.polar.template.service

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Apple
import io.github.jan.supabase.auth.providers.Github
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.Email
import org.springframework.stereotype.Service

@Service
class AuthService(private val supabaseClient: SupabaseClient) {

    suspend fun registerWithEmail(email: String, password: String): String? {
        val result = this.supabaseClient.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }

        return result?.id
    }

    suspend fun loginWithEmail(email: String, password: String): String? {
        this.supabaseClient.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }

        return this.supabaseClient.auth.currentAccessTokenOrNull()
    }

    suspend fun googleAuthUrl(redirectUrl: String): String {
        return this.supabaseClient.auth.getOAuthUrl(Google, redirectUrl)
    }

    suspend fun gitHubAuthUrl(redirectUrl: String): String {
        return this.supabaseClient.auth.getOAuthUrl(Github, redirectUrl)
    }

    suspend fun appleAuthUrl(redirectUrl: String): String {
        return this.supabaseClient.auth.getOAuthUrl(Apple, redirectUrl)
    }

    suspend fun importSessionFromFrontend(jwt: String) {
        supabaseClient.auth.importAuthToken(jwt, retrieveUser = true)
    }

}