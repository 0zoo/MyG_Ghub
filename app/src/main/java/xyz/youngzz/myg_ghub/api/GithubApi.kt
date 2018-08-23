package xyz.youngzz.myg_ghub.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import xyz.youngzz.myg_ghub.api.model.ReceivedEventsResponse
import xyz.youngzz.myg_ghub.api.model.RepoSearchResponse
import xyz.youngzz.myg_ghub.api.model.User

interface GithubApi {
    @GET("search/repositories")
    fun searchRepository(@Query("q") query: String): Call<RepoSearchResponse>

    @GET("user")
    fun getCurrentUser(): Call<User>

    @GET("users/{login}/received_events")
    fun recievedEvents(@Path("login") login : String): Call<List<ReceivedEventsResponse>>

}
