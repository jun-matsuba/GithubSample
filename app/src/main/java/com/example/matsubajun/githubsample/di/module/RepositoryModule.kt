package com.example.matsubajun.githubsample.di.module

import com.example.matsubajun.githubsample.data.repository.GithubRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { GithubRepository(get(), get()) }
}