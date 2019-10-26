package com.daredevil.twitchauth.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.daredevil.twitchauth.model.User

@Dao
interface UserDao {
    @Insert(onConflict = REPLACE)
    fun insertUser(user: User)

    @Query("SELECT * FROM users LIMIT 1")
    fun getUser(): LiveData<User>
}