package com.example.matsubajun.githubsample.ui

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.matsubajun.githubsample.BuildConfig
import com.example.matsubajun.githubsample.data.repository.GithubRepository
import com.example.matsubajun.githubsample.ui.common.BaseViewModel

class MainViewModel(application: Application, private val repository: GithubRepository) : BaseViewModel(application) {

    private val loginName = MutableLiveData<String>()
    val userApiLiveData = Transformations.switchMap(loginName) {
        repository.getUserData(it)
        return@switchMap repository.getUserDataFromLocal(it)
    }

    init {
        loginName.value = BuildConfig.GITHUB_USER_NAME
    }
}