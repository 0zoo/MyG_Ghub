package xyz.youngzz.myg_ghub.api.model

import com.google.gson.annotations.SerializedName


data class GithubRepo(@field:SerializedName("full_name") val fullName: String, val owner: GithubOwner)

data class RepoSearchResponse(@field:SerializedName("total_count") val totalCount: Int,
                              val items: List<GithubRepo>)

data class Repo(val owner: GithubOwner,
                val id: Int,
                val name : String,
                @field:SerializedName("full_name")
                val fullName: String,
                val private: Boolean,
                val description: String?,
                @field:SerializedName("created_at")
                val createdAt: String,
                @field:SerializedName("updated_at")
                val updatedAt: String,
                @field:SerializedName("pushed_at")
                val pushedAt: String,
                val size: Int,
                @field:SerializedName("stargazers_count")
                val stargazersCount: Int,
                @field:SerializedName("watchers_count")
                val watchersCount: Int,
                @field:SerializedName("forks_count")
                val forksCount: Int,
                @field:SerializedName("open_issues_count")
                val openIssuesCount: Int,
                val language: String?,
                @field:SerializedName("default_branch")
                val defaultBranch: String )

