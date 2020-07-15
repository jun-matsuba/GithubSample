package com.example.matsubajun.githubsample.data.repository

import androidx.lifecycle.LiveData
import com.example.matsubajun.githubsample.data.Resource
import com.example.matsubajun.githubsample.data.db.UserDao
import com.example.matsubajun.githubsample.data.network.NetworkCallHandler
import com.example.matsubajun.githubsample.data.network.api.GithubApi
import com.example.matsubajun.githubsample.domain.model.FollowList
import com.example.matsubajun.githubsample.domain.model.GithubUser
import com.example.matsubajun.githubsample.domain.model.UserDetail
import com.example.matsubajun.githubsample.domain.request.FollowRequest
import com.example.matsubajun.githubsample.util.CacheTimeLimiter

class GithubRepository(val githubApi: GithubApi, val userDao: UserDao) {

    companion object {
        const val PER_PAGE = 10
    }

    val userDataAndRepositoriesCache = HashMap<String, UserDetail>()
    val cacheTimeLimiter = CacheTimeLimiter<String>()

    fun getUserDataFromLocal(loginName: String) = userDao.selectByLogin(loginName)

    fun getUserData(loginName: String): LiveData<Resource<GithubUser>> {
        return object : NetworkCallHandler<GithubUser>() {

            override suspend fun getResult(): GithubUser {
                val githubUser = githubApi.getUser(loginName).await()
                userDao.insert(githubUser)
                return githubUser
            }
        }.makeCall()
    }

    fun getFollowUsers(isFollow: Boolean, followRequest: FollowRequest): LiveData<Resource<FollowList>> {
        return object : NetworkCallHandler<FollowList>() {

            override suspend fun getResult(): FollowList {

                val followList = when (isFollow) {
                    false -> {
                        githubApi.getFollowers(followRequest.user, followRequest.page, PER_PAGE).await()
                    }
                    true -> {
                        githubApi.getFollowings(followRequest.user, followRequest.page, PER_PAGE).await()
                    }
                }

                val hasNextPage = followList.size == PER_PAGE

                return FollowList(followList, hasNextPage)
            }
        }.makeCall()
    }

    fun getUserDataAndRepositories(loginName: String): LiveData<Resource<UserDetail>> {
        return object : NetworkCallHandler<UserDetail>() {

            override suspend fun getResult(): UserDetail {
                if (!cacheTimeLimiter.shouldFetch(loginName) && userDataAndRepositoriesCache.contains(loginName)) {
                    return userDataAndRepositoriesCache[loginName]!!
                }

                val githubUserDeferred = githubApi.getUser(loginName)
                val reposDeferred = githubApi.getRepositories(loginName)

                val userDetail = UserDetail(githubUserDeferred.await(), reposDeferred.await())
                userDataAndRepositoriesCache[loginName] = userDetail
                return userDetail
            }
        }.makeCall()
    }
}