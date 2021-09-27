package com.rodrigues.githubrepositories.ui.repositorieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.rodrigues.domain.model.GitRepository
import com.rodrigues.domain.model.util.ErrorResponseBody
import com.rodrigues.domain.model.util.Request
import com.rodrigues.domain.model.util.Status
import com.rodrigues.githubrepositories.R
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
        outState.putSerializable("ADAPTER_LIST", adapter.getRepositoriesList())
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.getSerializable("ADAPTER_LIST")?.let {
            (it as? ArrayList<GitRepository>)?.let { repositories ->
                adapter.addRepositories(repositories)
            }
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
             this.setupWithNavController(findNavController())
        }
    }

    private fun setupRepositoriesList() {
        binding.repositoriesRecyclerView.let { list ->
            list.adapter = adapter
            list.layoutAnimation = AnimationUtils.loadLayoutAnimation(
                list.context,
                R.anim.layout_animation_fall_down
            )
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.resetRepositoriesList()
            viewModel.resetRepositoriesList()
        }
    }

    private fun setupObservables() {
        viewModel.repositoriesData.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.IDLE -> {
                    hideProgressIndicators()
                }
                Status.LOADING -> {
                    if (viewModel.currentPage != 1) binding.pageProgressIndicator.isVisible = true
                    else binding.progressIndicator.show()
                }
                Status.SUCCESS -> {
                    hideProgressIndicators()

                    it.data?.let { data -> addRepositoriesToList(data) }
                        ?: showGenericErrorMessage()
                    viewModel.repositoriesData.postValue(Request.idle())
                }
                else -> {
                    hideProgressIndicators()

                    if (!it.error?.message.isNullOrBlank()) showServerErrorMessage(it.error!!)
                    else showGenericErrorMessage()

                    viewModel.repositoriesData.postValue(Request.idle())
                }
            }
        }
    }

    private fun addRepositoriesToList(repositories: List<GitRepository>) {
        adapter.addRepositories(ArrayList(repositories))
        if (viewModel.currentPage == 1) binding.repositoriesRecyclerView.scheduleLayoutAnimation()
    }

    private fun showGenericErrorMessage() {
        AlertDialog.Builder(activity as AppCompatActivity)
            .setTitle("Oops... ")
            .setMessage("Looks like we had a problem :(\nPlease try again")
            .setPositiveButton("OK", null)
            .create()
            .show()
    }

    private fun showServerErrorMessage(body: ErrorResponseBody) {
        AlertDialog.Builder(activity as AppCompatActivity)
            .setMessage(body.message)
            .setPositiveButton("OK", null)
            .create()
            .show()
    }

    private fun navigateToDetail(repository: GitRepository) {
        val action = RepoListFragmentDirections.actionRepoListFragmentToRepoDetailsFragment(repository)
        findNavController().navigate(action)
    }

    private fun hideProgressIndicators() {
        binding.progressIndicator.hide()
        binding.pageProgressIndicator.isVisible = false
        binding.swipeRefreshLayout.isRefreshing = false
    }
}