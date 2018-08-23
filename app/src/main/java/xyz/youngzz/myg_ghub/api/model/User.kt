package xyz.youngzz.myg_ghub.api.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

const val NOT_SET = "Not Set"

data class User (
        val login:String,
        val id: Int,

        @field:SerializedName("avatar_url")
        val avatarUrl: String,
        val name: String = NOT_SET,
        val type : String,

        val company : String = NOT_SET,
        val blog : String = NOT_SET,
        val location : String = NOT_SET,
        val email : String = NOT_SET,
        val bio : String = NOT_SET,

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
) : Serializable


data class Org(
        val login:String,
        val id: Int,

        @field:SerializedName("avatar_url")
        val avatarUrl: String
)
