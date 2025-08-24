package org.polar.template.controller

import org.polar.template.service.AuthService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {


}