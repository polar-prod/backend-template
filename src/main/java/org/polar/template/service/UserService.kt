package org.polar.template.service

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.user.UserInfo
import org.springframework.stereotype.Service

@Service
class UserService(private val supabaseClient: SupabaseClient) {

    suspend fun currentUser(): UserInfo? {
        return supabaseClient.auth.currentUserOrNull()
    }
}