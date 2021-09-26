package com.rodrigues.domain.repository

import com.rodrigues.domain.model.GitRepoLanguage
import com.rodrigues.domain.model.GitRepoSortOption
import com.rodrigues.domain.model.GitRepository

interface IRepositoriesRepository {

    suspend fun getGitRepositories(
        page: Int? = null,
        language: GitRepoLanguage,
        sortOption: GitRepoSortOption
    ): List<GitRepository>
}