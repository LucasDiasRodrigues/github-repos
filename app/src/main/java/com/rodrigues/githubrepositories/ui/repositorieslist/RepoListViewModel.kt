package com.rodrigues.githubrepositories.ui.repositorieslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigues.data.repository.GitRepositoriesRepository
import com.rodrigues.domain.model.GitRepoLanguage
import com.rodrigues.domain.model.GitRepoSortOption
import com.rodrigues.domain.model.GitRepository
import com.rodrigues.domain.model.util.Request
import com.rodrigues.domain.usecases.GitRepositoriesUseCases
import com.rodrigues.githubrepositories.util.EspressoIdlingResource
import com.rodrigues.githubrepositories.util.getErrorResponseBody
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RepoListViewModel : ViewModel() {
    var repositoriesUseCases = GitRepositoriesUseCases(GitRepositoriesRepository())

    val repositoriesData: MutableLiveData<Request<List<GitRepository>>> = MutableLiveData()
    var currentPage: Int = 1
    var lastSuccessPage: Int = 0

    fun getRepositoriesList() {
        if (lastSuccessPage == currentPage) return

        viewModelScope.launch {
            repositoriesData.postValue(Request.loading())
            EspressoIdlingResource.increment()

            try {
                repositoriesData.postValue(
                    Request.success(
                        repositoriesUseCases.getGitRepositories(
                            currentPage,
                            GitRepoLanguage.KOTLIN,
                            GitRepoSortOption.STARS
                        )
                    )
                )
                lastSuccessPage = currentPage

                EspressoIdlingResource.decrement()
            } catch (e: HttpException) {
                e.printStackTrace()
                repositoriesData.postValue(Request.error(e.message(), e.getErrorResponseBody()))

                EspressoIdlingResource.decrement()
            } catch (e: Exception) {
                e.printStackTrace()
                repositoriesData.postValue(Request.error(e.message))

                EspressoIdlingResource.decrement()
            }
        }
    }

    fun getNextRepositoriesPage() {
        currentPage++
        getRepositoriesList()
    }

    fun resetRepositoriesList() {
        currentPage = 1
        lastSuccessPage = 0
        getRepositoriesList()
    }
}