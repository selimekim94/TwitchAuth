package com.daredevil.twitchauth.api

import androidx.lifecycle.LiveData
import com.daredevil.mvitest.util.ApiResponse
import com.daredevil.twitchauth.model.UserResponse
import retrofit2.http.*

interface TwitchApiService {
    @GET("users")
    fun getUser(
        @Header("Authorization") authorization: String,
        @Header("Accept") accept: String = "application/vnd.twitchtv.v5+json",
        @Header("Client-ID") clientId: String
    ): LiveData<ApiResponse<UserResponse>>
}