package org.polar.template.model

import java.util.UUID

enum class Role { USER, ADMIN }

data class User(
    val id: UUID = UUID.randomUUID(),
    val email: String,
    val role: Role
)