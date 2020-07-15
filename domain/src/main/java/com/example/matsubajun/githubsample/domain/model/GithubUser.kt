package com.example.matsubajun.githubsample.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.util.Date

@Entity
data class GithubUser(
    @PrimaryKey val login: String,
    @Json(name = "node_id") val nodeId: String,
    val id: Int,
    @Json(name = "avatar_url") val avatarUrl: String,
    @Json(name = "gravatar_id") val gravaterId: String,
    val url: String,
    @Json(name = "html_url") val htmlUrl: String,
    @Json(name = "followers_url") val followersUrl: String,
    @Json(name = "following_url") val followingUrl: String,
    @Json(name = "gists_url") val gistsUrl: String,
    @Json(name = "starred_url") val starredUrl: String,
    @Json(name = "subscriptions_url") val subscriptionUrl: String,
    @Json(name = "organizations_url") val organizationsUrl: String,
    @Json(name = "repos_url") val reposUrl: String,
    @Json(name = "events_url") val eventsUrl: String,
    @Json(name = "received_events_url") val receivedEventsUrl: String,
    val type: String,
    @Json(name = "site_admin") val siteAdmin: Boolean,
    var name: String? = null,
    var company: String? = null,
    var blog: String? = null,
    var location: String? = null,
    var email: String? = null,
    var bio: String? = null,
    @Json(name = "public_repos") val publicRepos: Int,
    @Json(name = "public_gists") val publicGists: Int,
    val followers: Int,
    val following: Int,
    @Json(name = "created_at") val createdAt: Date,
    @Json(name = "updated_at") val updatedAt: Date
)
