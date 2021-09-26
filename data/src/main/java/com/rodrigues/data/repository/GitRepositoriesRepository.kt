package com.rodrigues.data.repository

import com.rodrigues.data.mapper.getRepositoriesList
import com.rodrigues.data.remote.api.GitRepositoriesApi
import com.rodrigues.data.remote.retrofit.RetrofitInstance
import com.rodrigues.domain.model.GitRepoLanguage
import com.rodrigues.domain.model.GitRepoSortOption
import com.rodrigues.domain.model.GitRepository
import com.rodrigues.domain.repository.IRepositoriesRepository

class GitRepositoriesRepository : IRepositoriesRepository {

    private val repositoriesService by lazy {
        RetrofitInstance.retrofit.create(GitRepositoriesApi::class.java)
    }

    override suspend fun getGitRepositories(
        page: Int?,
        language: GitRepoLanguage,
        sortOption: GitRepoSortOption
    ): List<GitRepository> {
        return repositoriesService.getGitRepositories(page, language, sortOption)
            .getRepositoriesList()
    }
}