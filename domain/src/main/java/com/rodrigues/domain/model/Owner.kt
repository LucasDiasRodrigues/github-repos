package com.rodrigues.domain.model

import java.io.Serializable

data class Owner(
    val id: Long,
    val login: String,
    val type: String,
    val avatarUrl: String
): Serializable