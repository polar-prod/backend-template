package org.polar.template.model

import io.github.jan.supabase.auth.mfa.AuthenticatorAssuranceLevel

data class AuthenticatorAssuranceLevelResponse(
    val currentLevel: AuthenticatorAssuranceLevel,
    val nextLevel: AuthenticatorAssuranceLevel
)