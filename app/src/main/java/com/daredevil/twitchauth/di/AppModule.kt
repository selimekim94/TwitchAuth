package com.daredevil.twitchauth.di

import android.app.Application
import androidx.room.Room
import com.daredevil.twitchauth.api.OAuthApiService
import com.daredevil.twitchauth.api.TwitchApiService
import com.daredevil.twitchauth.db.TDatabase
import com.daredevil.twitchauth.db.TokenDao
import com.daredevil.twitchauth.db.UserDao
import com.daredevil.twitchauth.util.Constants
import com.daredevil.twitchauth.util.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    @Named("OAuthClient")
    fun provideOauthClient(): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.OAUTH_ENDPOINT)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
    }

    @Provides
    @Singleton
    @Named("TwitchClient")
    fun provideTwitchClient(): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.TWITCH_ENDPOINT)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
    }

    @Provides
    @Singleton
    fun provideOAuthService(@Named("OAuthClient") retrofit: Retrofit): OAuthApiService {
        return retrofit.create(OAuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideTwitchService(@Named("TwitchClient") retrofit: Retrofit): TwitchApiService {
        return retrofit.create(TwitchApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDb(application: Application): TDatabase {
        return Room.databaseBuilder(
            application.applicationContext, TDatabase::class.java,
            "t_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTokenDao(db: TDatabase): TokenDao {
        return db.tokenDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(db: TDatabase): UserDao {
        return db.userDao()
    }
}