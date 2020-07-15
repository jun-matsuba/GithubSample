package com.example.matsubajun.githubsample.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.matsubajun.githubsample.data.Resource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

abstract class NetworkCallHandler<ResultType> {

    fun makeCall(): LiveData<Resource<ResultType>> {
        val result = MutableLiveData<Resource<ResultType>>()
        result.value = Resource.loading(null)
        GlobalScope.async {
            try {
                val response = getResult()
                result.postValue(Resource.success(response))
            } catch (e: Exception) {
                result.postValue(Resource.error(e))
            }
        }
        return result
    }

    protected abstract suspend fun getResult(): ResultType

}
