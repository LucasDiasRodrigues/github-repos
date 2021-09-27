package com.rodrigues.githubrepositories.ui.repositorydetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.rodrigues.githubrepositories.R
import com.rodrigues.githubrepositories.databinding.FragmentRepoDetailBinding
import com.rodrigues.githubrepositories.util.DateUtils
import com.squareup.picasso.Picasso


class RepoDetailsFragment : Fragment() {
    private var _binding: FragmentRepoDetailBinding? = null
    private val binding get() = _binding!!

    val args: RepoDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRepoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRepositoryInformation()
        setupButtons()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupToolbar() {
        with(binding.toolbar) {
            (activity as AppCompatActivity).setSupportActionBar(this)
            this.setupWithNavController(findNavController())
        }
    }

    private fun setupRepositoryInformation() {
        val repository = args.repository

        binding.repoName.text = repository.fullName
        binding.repoDescription.text = repository.description
        binding.ownerName.text = repository.owner.login

        binding.starsCount.text =
            getString(R.string.starsCount, repository.stargazersCount.toString())
        binding.forksCount.text = getString(R.string.forksCount, repository.forksCount.toString())
        binding.ownerType.text = getString(R.string.ownerType, repository.owner.type)

        val updatedAt = DateUtils.formatDateFromString(repository.updatedAt)
        binding.repoLastUpdate.text = getString(R.string.repoUpdatedAt, updatedAt)

        Picasso.with(context).load(repository.owner.avatarUrl).into(binding.ownerPic)
    }

    private fun setupButtons() {
        binding.gitPageButton.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(args.repository.htmlUrl)
            startActivity(intent)
        }
    }
}