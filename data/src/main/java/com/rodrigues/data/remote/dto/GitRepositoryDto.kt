package com.rodrigues.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GitRepositoryDto(
    val id: Long,
    val description: String,
    val owner: GitRepoOwnerDto,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("html_url") val htmlUrl: String,
    @SerializedName("stargazers_count") val stargazersCount: Long,
    @SerializedName("forks_count") val forksCount: Long,
    @SerializedName("updated_at") val updatedAt: String,
)
