package org.polar.template.service

import com.google.firebase.auth.FirebaseAuth
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.Key
import java.util.Date
import java.util.concurrent.TimeUnit

@Service
class AuthService(
    @Value("\${jwt.secret}") private val jwtSecret: String
) {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val key: Key = Keys.hmacShaKeyFor(jwtSecret.toByteArray())

    fun createJwtToken(userId: String, ip: String, userAgent: String): String {
        val now = System.currentTimeMillis()
        val expiry = now + TimeUnit.DAYS.toMillis(7)

        return Jwts.builder()
            .subject(userId)
            .claim("ip", ip)
            .claim("userAgent", userAgent)
            .issuedAt(Date(now))
            .expiration(Date(expiry))
            .signWith(key)

            .compact()
    }

    fun verifyFirebaseToken(idToken: String) = this.firebaseAuth.verifyIdToken(idToken)
}