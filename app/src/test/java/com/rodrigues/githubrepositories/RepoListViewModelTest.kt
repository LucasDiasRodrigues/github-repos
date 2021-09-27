package com.rodrigues.githubrepositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.whenever
import com.rodrigues.domain.model.GitRepoLanguage
import com.rodrigues.domain.model.GitRepoSortOption
import com.rodrigues.domain.usecases.GitRepositoriesUseCases
import com.rodrigues.githubrepositories.ui.repositorieslist.RepoListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class RepoListViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    private var viewModel = RepoListViewModel()
    private var repositoriesUseCasesMock = Mockito.mock(GitRepositoriesUseCases::class.java)

    @Before
    fun setUp() {
        viewModel.repositoriesUseCases = repositoriesUseCasesMock

        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `it should call usecases to get the repositories list`() {
        runBlockingTest {
            whenever(repositoriesUseCasesMock.getGitRepositories(viewModel.currentPage,
                GitRepoLanguage.KOTLIN,
                GitRepoSortOption.STARS))
                .thenReturn(emptyList())

            viewModel.getRepositoriesList()

            Mockito.verify(repositoriesUseCasesMock, times(1))
                .getGitRepositories(any(), any(), any())
        }
    }

    @Test
    fun `it should get next repositories page`() {
        runBlockingTest {
            whenever(repositoriesUseCasesMock.getGitRepositories(viewModel.currentPage,
                GitRepoLanguage.KOTLIN,
                GitRepoSortOption.STARS))
                .thenReturn(emptyList())
            val initialPage = viewModel.currentPage

            viewModel.getNextRepositoriesPage()

            assertTrue(viewModel.currentPage == (initialPage + 1))
            assertTrue(viewModel.lastSuccessPage == viewModel.currentPage)
            Mockito.verify(repositoriesUseCasesMock, times(1))
                .getGitRepositories(any(), any(), any())
        }
    }

    @Test
    fun `it should reset pages counter`() {
        runBlockingTest {
            whenever(repositoriesUseCasesMock.getGitRepositories(viewModel.currentPage,
                GitRepoLanguage.KOTLIN,
                GitRepoSortOption.STARS))
                .thenReturn(emptyList())
            viewModel.getNextRepositoriesPage()
            viewModel.getNextRepositoriesPage()
            viewModel.getNextRepositoriesPage()
            val initialPage = viewModel.currentPage
            assertTrue(viewModel.currentPage == 4)

            viewModel.resetRepositoriesList()

            assertTrue(viewModel.currentPage == 1)
        }
    }
}