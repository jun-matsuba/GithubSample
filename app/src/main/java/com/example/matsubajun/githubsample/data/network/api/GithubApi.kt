package com.example.matsubajun.githubsample.data.network.api

import com.example.matsubajun.githubsample.BuildConfig
import com.example.matsubajun.githubsample.domain.model.Follow
import com.example.matsubajun.githubsample.domain.model.GithubUser
import com.example.matsubajun.githubsample.domain.model.Repo
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    @Headers("Authorization: token " + BuildConfig.GITHUB_ACCESS_TOKEN)
    @GET("/users/{user}")
    fun getUser(@Path("user") user: String): Deferred<GithubUser>

    @Headers("Authorization: token " + BuildConfig.GITHUB_ACCESS_TOKEN)
    @GET("/users/{user}/following")
    fun getFollowings(@Path("user") user: String, @Query("page") page: Int, @Query("per_page") per_page: Int): Deferred<List<Follow>>

    @Headers("Authorization: token " + BuildConfig.GITHUB_ACCESS_TOKEN)
    @GET("/users/{user}/followers")
    fun getFollowers(@Path("user") user: String, @Query("page") page: Int, @Query("per_page") per_page: Int): Deferred<List<Follow>>

    @Headers("Authorization: token " + BuildConfig.GITHUB_ACCESS_TOKEN)
    @GET("/users/{username}/repos")
    fun getRepositories(@Path("username") username: String): Deferred<List<Repo>>
}