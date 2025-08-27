package org.polar.template.config

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SuperbaseConfig {

    @Value("\${supabase.url}")
    private lateinit var supabaseUrl: String

    @Value("\${supabase.key}")
    private lateinit var supabaseKey: String

    @Bean
    fun supabaseClient(): SupabaseClient {
        return createSupabaseClient(supabaseUrl, supabaseKey) {
            install(Auth)
            // Weitere Dienste: Postgrest, Storage, etc.
        }
    }
}