package com.example.matsubajun.githubsample.data.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiRequestInterceptor : Interceptor {

    companion object {
        const val MAX_RETRY_COUNT = 3
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val request = builder.build()
        var response = chain.proceed(request)

        // Retry
        var retryCnt = 0
        while (!response.isSuccessful && retryCnt < MAX_RETRY_COUNT) {
            retryCnt++
            response = chain.proceed(request)
        }

        return response
    }
}