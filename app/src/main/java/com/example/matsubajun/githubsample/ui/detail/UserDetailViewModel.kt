package com.example.matsubajun.githubsample.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.matsubajun.githubsample.data.Resource
import com.example.matsubajun.githubsample.data.repository.GithubRepository
import com.example.matsubajun.githubsample.domain.model.UserDetail
import com.example.matsubajun.githubsample.ui.common.BaseViewModel

class UserDetailViewModel(application: Application, private val githubRepository: GithubRepository) : BaseViewModel(application) {

    private val loginName: MutableLiveData<String> = MutableLiveData()

    val userDetailLiveData: LiveData<Resource<UserDetail>> = Transformations.switchMap(loginName) {
        loginName.value?.let {
            githubRepository.getUserDataAndRepositories(it)
        }
    }

    fun setParam(loginName: String) {
        if (!this.loginName.value.isNullOrEmpty()) {
            return
        }
        this.loginName.value = loginName
    }

}
