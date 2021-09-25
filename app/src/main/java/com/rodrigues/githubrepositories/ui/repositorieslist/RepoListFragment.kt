package com.rodrigues.githubrepositories.ui.repositorieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rodrigues.githubrepositories.databinding.FragmentRepoListBinding

class RepoListFragment : Fragment() {
    private var _binding: FragmentRepoListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RepoListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
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
    }

    private fun setupToolbar() {
        with(binding.toolbar) {
            (activity as AppCompatActivity).setSupportActionBar(this)
           // this.setupWithNavController(navController)
        }
    }

    private fun setupObservables() {

    }

    companion object {
        fun newInstance() = RepoListFragment()
    }
}