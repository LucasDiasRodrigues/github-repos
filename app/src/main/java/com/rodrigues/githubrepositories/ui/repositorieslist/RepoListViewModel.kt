package com.rodrigues.githubrepositories.ui.repositorieslist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigues.data.repository.GitRepositoriesRepository
import com.rodrigues.domain.model.GitRepoLanguage
import com.rodrigues.domain.model.GitRepoSortOption
import com.rodrigues.domain.model.GitRepository
import com.rodrigues.domain.model.util.Request
import com.rodrigues.domain.usecases.GitRepositoriesUseCases
import kotlinx.coroutines.launch
import java.lang.Exception

class RepoListViewModel : ViewModel() {
    val repositoriesUseCases = GitRepositoriesUseCases(GitRepositoriesRepository())

    val repositoriesData: MutableLiveData<Request<List<GitRepository>>> = MutableLiveData()

    fun getRepositoriesList(){
        viewModelScope.launch {
            repositoriesData.postValue(Request.loading())

            try {
                repositoriesData.postValue(Request.success(repositoriesUseCases.getGitRepositories(
                    page = 1,
                    GitRepoLanguage.KOTLIN,
                    GitRepoSortOption.STARS
                )))
                Log.i("LDR", "Success")
            } catch (e: Exception){
                e.printStackTrace()
                repositoriesData.postValue(Request.error(e.message))
                Log.i("LDR", "Error")
            }
        }
    }
}