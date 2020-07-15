package com.example.matsubajun.githubsample.util

import android.os.SystemClock
import androidx.collection.ArrayMap
import java.util.concurrent.TimeUnit

class CacheTimeLimiter<in KEY> {
    private val CACHE_TIME = TimeUnit.MINUTES.toMillis(10L)
    private val timestamps = ArrayMap<KEY, Long>()

    @Synchronized
    fun shouldFetch(key: KEY): Boolean {
        val lastFetched = timestamps[key]
        val now = now()
        if (lastFetched == null) {
            timestamps[key] = now
            return true
        }
        if (now - lastFetched > CACHE_TIME) {
            timestamps[key] = now
            return true
        }
        return false
    }

    private fun now() = SystemClock.uptimeMillis()

    @Synchronized
    fun reset(key: KEY) {
        timestamps.remove(key)
    }
}