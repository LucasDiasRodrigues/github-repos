package com.rodrigues.domain.model

data class Owner(
    val id: Long,
    val login: String,
    val type: String,
    val avatarUrl: String
)