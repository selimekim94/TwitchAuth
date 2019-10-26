package com.daredevil.twitchauth.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.daredevil.twitchauth.model.Token
import com.daredevil.twitchauth.model.User

@Database(entities = [Token::class, User::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TDatabase : RoomDatabase() {

    abstract fun tokenDao(): TokenDao

    abstract fun userDao(): UserDao
}