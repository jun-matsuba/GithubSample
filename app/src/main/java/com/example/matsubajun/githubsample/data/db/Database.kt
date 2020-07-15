package com.example.matsubajun.githubsample.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.matsubajun.githubsample.domain.model.GithubUser

@Database(entities = [GithubUser::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun userDao(): UserDao
}