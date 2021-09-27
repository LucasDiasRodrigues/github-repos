package com.rodrigues.domain.usecases

import com.rodrigues.domain.model.GitRepoLanguage
import com.rodrigues.domain.model.GitRepoSortOption
import com.rodrigues.domain.model.GitRepository
import com.rodrigues.domain.repository.IRepositoriesRepository

class GitRepositoriesUseCases(private val repositoriesRepository: IRepositoriesRepository) {
    suspend fun getGitRepositories(
        page: Int?,
        language: GitRepoLanguage,
        sortOption: GitRepoSortOption
    ): List<GitRepository> {
        return repositoriesRepository.getGitRepositories(page, language, sortOption)
    }
}