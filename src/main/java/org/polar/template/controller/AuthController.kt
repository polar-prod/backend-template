package org.polar.template.controller

import io.github.jan.supabase.SupabaseClient
import org.polar.template.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @Autowired
    lateinit var supabase: SupabaseClient
}