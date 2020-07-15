package com.example.matsubajun.githubsample.domain.model

import com.squareup.moshi.Json

data class ErrorResponse(
    val message: String,
    @Json(name = "documentation_url") val documentationUrl: String
)