package xyz.youngzz.myg_ghub.api.model

import com.google.gson.annotations.SerializedName

data class User(
        val login:String,
        val name: String,
        @field:SerializedName("avatar_url")
        val avatarUrl: String,
        val followers: Int = 0,
        val following: Int = 0,
        @field:SerializedName("public_repos")
        val publicRepos: String
)