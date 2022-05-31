package com.cubing.githubuser.api

import com.cubing.githubuser.model.User
import com.cubing.githubuser.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username: String
    ) : Call<User>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowersUser(
        @Path("username") username: String
    ) : Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowingUser(
        @Path("username") username: String
    ) : Call<ArrayList<User>>

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchedUser(
        @Query("q") query: String
    ): Call<UserResponse>
}