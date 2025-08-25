package org.polar.template.config

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class SuperbaseConfig(
    @Value("\${supabase.url}") private val superbaseUrl: String,
    @Value("\${supabase.key}") private val superbaseKey: String
) {

    lateinit var supabaseClient: SupabaseClient

    @PostConstruct
    fun init() {
        supabaseClient = createSupabaseClient(superbaseUrl, superbaseKey) {
            install(Auth)
            //TODO install Postgrest oder other services
        }

        print("Supabase Client successfully initialized")
    }
}