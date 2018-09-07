package xyz.youngzz.myg_ghub.api.model

import com.google.gson.annotations.SerializedName

const val NOT_SET = "Not Set"

data class User (
        val login:String,
        val id: Int,

        @field:SerializedName("avatar_url")
        val avatarUrl: String,
        @field:SerializedName("name")
        private val _name: String?,
        val type : String,

        @field:SerializedName("company")
        private val _company : String?,
        @field:SerializedName("blog")
        private val _blog : String?,
        @field:SerializedName("location")
        private val _location : String?,
        @field:SerializedName("email")
        private val _email : String?,
        val bio : String,

        val followers: Int = 0,
        val following: Int = 0,

        @field:SerializedName("public_repos")
        val publicRepos: Int = 0,
        @field:SerializedName("public_gists")
        val publicGists: Int = 0,

        @field:SerializedName("created_at")
        val createdAt: String = "",
        @field:SerializedName("updated_at")
        val updatedAt: String = "",

        var starredCount : Int = 0
){
        val name get() = _name ?: NOT_SET

        val company get() = _company ?: NOT_SET

        val blog get() = _blog ?: NOT_SET

        val location get() = _location ?: NOT_SET

        val email get() = _email ?: NOT_SET

}




data class Org(
        val login:String,
        val id: Int,

        @field:SerializedName("avatar_url")
        val avatarUrl: String
)



data class GithubOwner(val login: String,
                       @field:SerializedName("avatar_url") val avatarUrl: String)
