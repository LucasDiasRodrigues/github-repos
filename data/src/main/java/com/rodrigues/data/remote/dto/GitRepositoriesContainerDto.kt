package com.rodrigues.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GitRepositoriesContainerDto (
    @SerializedName("total_count") val totalCount: String,
    @SerializedName("incomplete_results") val incompleteResults: String,
    val items: List<GitRepositoryDto>,



    )