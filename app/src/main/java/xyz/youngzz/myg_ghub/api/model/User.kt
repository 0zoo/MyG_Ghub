package xyz.youngzz.myg_ghub.api.model

import com.google.gson.annotations.SerializedName

const val NONE = "none"

data class User(
        val login:String,
        val id: Int,

        @field:SerializedName("avatar_url")
        val avatarUrl: String,
        val userDetail: UserDetail?
)

data class UserDetail(
        val name: String = NONE,
        val type : String,

        val company : String = NONE,
        val blog : String = NONE,
        val location : String = NONE,
        val email : String = NONE,
        val bio : String = NONE,

        val followers: Int,
        val following: Int,

        @field:SerializedName("public_repos")
        val publicRepos: Int,
        @field:SerializedName("public_gists")
        val publicGists: Int,

        @field:SerializedName("created_at")
        val createdAt: String,
        @field:SerializedName("updated_at")
        val updatedAt: String
)

data class Org(
        val login:String,
        val id: Int,

        @field:SerializedName("avatar_url")
        val avatarUrl: String
)
