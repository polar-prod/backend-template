package org.polar.template.model

data class MfaEnrollResponse(
    val factorId: String,
    val qrCodeSvg: String
)