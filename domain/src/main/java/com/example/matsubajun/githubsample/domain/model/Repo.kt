package com.example.matsubajun.githubsample.domain.model

import com.squareup.moshi.Json

data class Repo(
    val id: Int,
    val name: String,
    @Json(name = "html_url") val htmlUrl: String,
    @Json(name = "stargazers_count") val stargazersCount: String,
    @Json(name = "forks_count") val forksCount: String,
    var language: String? = null,
    var description: String? = null,
    val fork: Boolean
)