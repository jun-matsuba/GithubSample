package com.example.matsubajun.githubsample.di.module

import com.example.matsubajun.githubsample.ui.MainViewModel
import com.example.matsubajun.githubsample.ui.detail.UserDetailViewModel
import com.example.matsubajun.githubsample.ui.follow.FollowListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
    viewModel { FollowListViewModel(get(), get()) }
    viewModel { UserDetailViewModel(get(), get()) }
}