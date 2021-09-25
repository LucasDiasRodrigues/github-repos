package com.rodrigues.domain.model

data class GitRepository(
    val id: Long,
    val fullName: String,
    val owner: Owner,
    val htmlUrl: String,
    val description: String,
    val stargazersCount: Long,
    val forksCount: Long,
    val updatedAt: String,
)