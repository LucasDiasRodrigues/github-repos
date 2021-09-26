package com.rodrigues.data.remote.api

import com.rodrigues.data.remote.dto.GitRepositoriesContainerDto
import com.rodrigues.domain.model.GitRepoLanguage
import com.rodrigues.domain.model.GitRepoSortOption
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitRepositoriesApi {
    @GET("search/repositories")
    suspend fun getGitRepositories(
        @Query("page") page: Int? = 1,
        @Query("q") query: GitRepoLanguage,
        @Query("sort")sort: GitRepoSortOption)
    : GitRepositoriesContainerDto
}