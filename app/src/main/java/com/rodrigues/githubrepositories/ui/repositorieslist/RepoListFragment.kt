package com.rodrigues.githubrepositories.ui.repositorieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rodrigues.domain.model.GitRepository
import com.rodrigues.domain.model.util.Request
import com.rodrigues.domain.model.util.Status
import com.rodrigues.githubrepositories.databinding.FragmentRepoListBinding

class RepoListFragment : Fragment() {
    private var _binding: FragmentRepoListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RepoListViewModel by viewModels()

    private var adapter = RepoListAdapter(
        clickListener = {
            navigateToDetail(it)
        },
        bottomListener = {
            viewModel.getNextRepositoriesPage()
        }
    )

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable("ADAPTER", adapter)
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.getSerializable("ADAPTER")?.let {
            adapter = it as? RepoListAdapter ?: return
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRepositoriesList()
        setupObservables()

        viewModel.getRepositoriesList()
    }


    private fun setupToolbar() {
        with(binding.toolbar) {
            (activity as AppCompatActivity).setSupportActionBar(this)
            // this.setupWithNavController(navController)
        }
    }

    private fun setupRepositoriesList() {
        binding.repositoriesRecyclerView.let { list ->
            list.isVisible = true
            list.adapter = adapter
        }
    }

    private fun setupObservables() {
        viewModel.repositoriesData.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.IDLE -> {
                    binding.progressIndicator.hide()
                    binding.pageProgressIndicator.isVisible = false
                }
                Status.LOADING -> {
                    if (viewModel.currentPage != 1) binding.pageProgressIndicator.isVisible = true
                    else binding.progressIndicator.show()
                }
                Status.SUCCESS -> {
                    binding.progressIndicator.hide()
                    binding.pageProgressIndicator.isVisible = false

                    it.data?.let { data -> addRepositoriesToList(data) } ?: showErrorMessage()
                    viewModel.repositoriesData.postValue(Request.idle())
                }
                else -> {
                    binding.progressIndicator.hide()
                    binding.pageProgressIndicator.isVisible = false

                    showErrorMessage()
                    viewModel.repositoriesData.postValue(Request.idle())
                }
            }
        }
    }

    private fun addRepositoriesToList(repositories: List<GitRepository>) {
        adapter.addRepositories(ArrayList(repositories))
    }

    private fun showErrorMessage() {
        AlertDialog.Builder(activity as AppCompatActivity)
            .setTitle("Oops... ")
            .setMessage("Looks like we had a problem :(\nPlease try again")
            .create()
            .show()
    }

    private fun navigateToDetail(repository: GitRepository) {

    }

    companion object {
        fun newInstance() = RepoListFragment()
    }
}