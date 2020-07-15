package com.example.matsubajun.githubsample.domain.model

data class UserDetail(
    val githubUser: GithubUser,
    val repos: List<Repo>
)