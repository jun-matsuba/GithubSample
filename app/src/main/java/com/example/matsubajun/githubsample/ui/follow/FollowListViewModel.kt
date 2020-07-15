package com.example.matsubajun.githubsample.ui.follow

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.matsubajun.githubsample.BuildConfig
import com.example.matsubajun.githubsample.data.Resource
import com.example.matsubajun.githubsample.data.Status
import com.example.matsubajun.githubsample.data.repository.GithubRepository
import com.example.matsubajun.githubsample.domain.model.FollowList
import com.example.matsubajun.githubsample.domain.request.FollowRequest
import com.example.matsubajun.githubsample.ui.common.BaseViewModel

class FollowListViewModel(application: Application, private val githubRepository: GithubRepository) : BaseViewModel(application) {

    val followRequest: MutableLiveData<FollowRequest> = MutableLiveData()
    private var isFollow = true

    val followListLiveData: LiveData<Resource<FollowList>> = Transformations.switchMap(followRequest) {
        followRequest.value?.let {
            githubRepository.getFollowUsers(isFollow, it)
        }
    }

    fun setFirstParam(isFollow: Boolean, name: String) {
        if (this.followRequest.value != null) return
        this.isFollow = isFollow
        this.followRequest.value = FollowRequest(1, name)
    }

    fun loadNextPage() {
        val hasNextPage = followListLiveData.value?.data?.hasNextPage ?: false
        if (!hasNextPage || followListLiveData.value?.status == Status.LOADING) return
        followRequest.value?.let {
            followRequest.value = FollowRequest(it.page + 1, BuildConfig.GITHUB_USER_NAME)
        }
    }
}