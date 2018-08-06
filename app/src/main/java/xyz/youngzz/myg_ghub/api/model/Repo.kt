package xyz.youngzz.myg_ghub.api.model

import com.google.gson.annotations.SerializedName


data class GithubRepo(@field:SerializedName("full_name") val fullName: String, val owner: GithubOwner)

data class RepoSearchResponse(@field:SerializedName("total_count") val totalCount: Int,
                              val items: List<GithubRepo>)

data class Repo(val id: Int, val name : String, val url : String)

