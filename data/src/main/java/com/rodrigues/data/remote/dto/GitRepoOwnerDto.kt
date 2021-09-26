package com.rodrigues.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GitRepoOwnerDto(
    val id: Long,
    val login: String,
    val type: String,
    @SerializedName("avatar_url") val avatarUrl: String
)
