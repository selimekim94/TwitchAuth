package com.daredevil.twitchauth.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import com.daredevil.twitchauth.model.Token
import androidx.lifecycle.LiveData
import androidx.room.Query


@Dao
interface TokenDao {

    @Insert(onConflict = REPLACE)
    fun insertToken(token: Token)

    @Query("SELECT * FROM tokens WHERE  id = 1")
    fun getToken(): LiveData<Token>
}