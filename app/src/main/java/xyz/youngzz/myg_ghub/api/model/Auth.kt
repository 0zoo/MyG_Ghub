package xyz.youngzz.myg_ghub.api.model

import com.google.gson.annotations.SerializedName

data class Auth(
        @field:SerializedName("access_token")
        val accessToken: String,
        @field:SerializedName("token_type")
        val tokenType: String) {
}

data class GithubOwner(val login: String,
                       @field:SerializedName("avatar_url") val avatarUrl: String)
