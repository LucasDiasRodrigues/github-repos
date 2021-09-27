package com.rodrigues.githubrepositories.ui.repositorieslist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rodrigues.domain.model.GitRepository
import com.rodrigues.githubrepositories.R
import com.rodrigues.githubrepositories.databinding.ItemRepositoriesListBinding
import com.rodrigues.githubrepositories.util.DateUtils
import com.squareup.picasso.Picasso

class RepoListAdapter(
    private val clickListener: ((GitRepository) -> Unit)? = null,
    private val bottomListener: (() -> Unit)? = null
) : RecyclerView.Adapter<RepoListAdapter.RepositoryViewHolder>() {

    private val repositories: ArrayList<GitRepository> = arrayListOf()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding =
            ItemRepositoriesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return RepositoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val repository = repositories?.get(position) ?: return

        holder.binding?.repoName?.text = repository.fullName
        holder.binding?.repoDescription?.text = repository.description
        holder.binding?.ownerName?.text = repository.owner.login

        context?.let {
            holder.binding?.starsCount?.text =
                it.getString(R.string.starsCount, repository.stargazersCount.toString())
            holder.binding?.forksCount?.text =
                it.getString(R.string.forksCount, repository.forksCount.toString())
            holder.binding?.ownerType?.text =
                it.getString(R.string.ownerType, repository.owner.type)

            val updatedAt = DateUtils.formatDateFromString(repository.updatedAt)
            holder.binding?.repoLastUpdate?.text =
                it.getString(R.string.repoUpdatedAt, updatedAt)

            Picasso.with(context).load(repository.owner.avatarUrl).into(holder.binding?.ownerPic)
        }

        clickListener?.let { listener ->
            holder.itemView.setOnClickListener { listener.invoke(repository) }
        }

        if (position > (itemCount - 2) && itemCount > 10) {
            bottomListener?.invoke()
        }
    }

    fun addRepositories(newRepositories: List<GitRepository>) {
        repositories.addAll(newRepositories)
        notifyDataSetChanged()
    }

    fun resetRepositoriesList() {
        repositories.clear()
        notifyDataSetChanged()
    }

    fun getRepositoriesList(): ArrayList<GitRepository> {
        return repositories
    }

    override fun getItemCount(): Int {
        return repositories.size ?: 0
    }

    inner class RepositoryViewHolder(val binding: ItemRepositoriesListBinding?) :
        RecyclerView.ViewHolder(binding?.root!!)
}