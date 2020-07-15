package com.example.matsubajun.githubsample.data.network

import android.content.Context
import androidx.annotation.StringRes
import com.example.matsubajun.githubsample.R
import com.example.matsubajun.githubsample.di.module.provideMoshi
import com.example.matsubajun.githubsample.domain.model.ErrorResponse
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class ErrorFeedBack {

    abstract fun getMessage(context: Context): String

    data class ApiErrorFeedback(val message: String, val code: Int) : ErrorFeedBack() {
        override fun getMessage(context: Context): String = message
    }

    data class ApplicationErrorFeedback(@StringRes val resId: Int) : ErrorFeedBack() {
        override fun getMessage(context: Context): String = context.getString(resId)
    }

    companion object {
        fun map(throwable: Throwable): ErrorFeedBack {
            return when (throwable) {
                is UnknownHostException, is ConnectException, is SocketTimeoutException -> {
                    ApplicationErrorFeedback(R.string.network_error)
                }
                is HttpException -> {
                    try {
                        val moshi = provideMoshi()
                        throwable.response()?.errorBody()?.let {
                            val message = moshi.adapter(ErrorResponse::class.java).fromJson(it.string())!!.message
                            ApiErrorFeedback(message, throwable.code())
                        } ?: throw Exception(Throwable())
                    } catch (e: Exception) {
                        when (throwable.code()) {
                            404 -> ApplicationErrorFeedback(R.string.network_error_not_found)
                            else -> ApplicationErrorFeedback(R.string.network_error_connection)
                        }
                    }
                }
                else -> ApplicationErrorFeedback(R.string.network_error_connection)
            }
        }
    }
}