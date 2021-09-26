package com.rodrigues.data.mapper

import com.rodrigues.data.remote.dto.GitRepoOwnerDto
import com.rodrigues.data.remote.dto.GitRepositoriesContainerDto
import com.rodrigues.data.remote.dto.GitRepositoryDto
import com.rodrigues.domain.model.GitRepository
import com.rodrigues.domain.model.Owner

fun GitRepositoriesContainerDto.getRepositoriesList(): List<GitRepository> {
    return items.map { it.toGitRepository() }
}

private fun GitRepositoryDto.toGitRepository(): GitRepository {
    return GitRepository(
        id,
        fullName,
        owner.toOwner(),
        htmlUrl,
        description ?: "",
        stargazersCount,
        forksCount,
        updatedAt
    )
}

private fun GitRepoOwnerDto.toOwner(): Owner {
    return Owner(id, login, type, avatarUrl)
}
