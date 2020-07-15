package com.example.matsubajun.githubsample.di.module

import android.app.Application
import androidx.room.Room
import com.example.matsubajun.githubsample.data.db.Database
import com.example.matsubajun.githubsample.data.db.UserDao
import com.example.matsubajun.githubsample.data.network.ApiRequestInterceptor
import com.example.matsubajun.githubsample.data.network.api.GithubApi
import com.example.matsubajun.githubsample.data.network.model.adapter.BooleanAdapter
import com.example.matsubajun.githubsample.data.network.model.adapter.DateAdapter
import com.example.matsubajun.githubsample.data.network.model.adapter.UriAdapter
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

const val API_BASE_URL = "https://api.github.com"

val apiModule = module {
    //client
    single { provideMoshi() }
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), get()) }

    //api
    single { provideGithubApi(get()) }

    //Cache
    //single { provideCacheProviders(get()) }

    //db
    single { provideDb(get()) }
    single { provideUserDao(get()) }
}

fun provideMoshi(): Moshi = Moshi.Builder().add(UriAdapter.FACTORY)
        .add(DateAdapter.FACTORY)
        .add(BooleanAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()

fun provideOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor.apply { httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC })
            .addInterceptor(ApiRequestInterceptor())
            .build()
}

fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(client)
                .build()

fun provideGithubApi(retrofit: Retrofit): GithubApi = retrofit.create(GithubApi::class.java)

fun provideDb(application: Application): Database {
    return Room
            .databaseBuilder(application, Database::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
}

fun provideUserDao(database: Database): UserDao = database.userDao()

//fun provideCacheProviders(application: Application): CacheProviders {
//    val cacheFile = application.cacheDir
//    val coroutinesCache = CoroutinesCache(CacheParams(10, MoshiMapper(), cacheFile))
//
//    return coroutinesCache.using(CacheProviders::class.java)
//}

