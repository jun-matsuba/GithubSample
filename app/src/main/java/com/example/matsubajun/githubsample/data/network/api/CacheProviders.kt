package com.example.matsubajun.githubsample.data.network.api

import com.epam.coroutinecache.annotations.Expirable
import com.epam.coroutinecache.annotations.LifeTime
import com.epam.coroutinecache.annotations.ProviderKey
import com.epam.coroutinecache.annotations.UseIfExpired
import com.example.matsubajun.githubsample.domain.model.Follow
import com.example.matsubajun.githubsample.domain.model.GithubUser
import com.example.matsubajun.githubsample.domain.model.Repo
import kotlinx.coroutines.Deferred
import java.util.concurrent.TimeUnit

private const val CACHE_MINUTES = 5L

interface CacheProviders {

    @ProviderKey("getUser")
    @LifeTime(value = CACHE_MINUTES, unit = TimeUnit.MINUTES)
    @Expirable
    @UseIfExpired
    fun getUser(data: Deferred<GithubUser>): Deferred<GithubUser>

    @ProviderKey("getFollowings")
    @LifeTime(value = CACHE_MINUTES, unit = TimeUnit.MINUTES)
    @Expirable
    @UseIfExpired
    fun geFollowings(data: Deferred<List<Follow>>): Deferred<List<Follow>>

    @ProviderKey("getFollowers")
    @LifeTime(value = CACHE_MINUTES, unit = TimeUnit.MINUTES)
    @Expirable
    @UseIfExpired
    fun geFollowers(data: Deferred<List<Follow>>): Deferred<List<Follow>>

    @ProviderKey("getRepositories")
    @LifeTime(value = CACHE_MINUTES, unit = TimeUnit.MINUTES)
    @Expirable
    @UseIfExpired
    fun getRepositories(data: Deferred<List<Repo>>): Deferred<List<Repo>>


}