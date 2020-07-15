package com.example.matsubajun.githubsample.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.matsubajun.githubsample.domain.model.GithubUser

@Dao
abstract class UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(githubUser: GithubUser)

    @Query("select * from githubuser where login = :login")
    abstract fun selectByLogin(login: String): LiveData<GithubUser>
}
