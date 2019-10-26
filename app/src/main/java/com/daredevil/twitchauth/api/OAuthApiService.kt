package com.daredevil.twitchauth.api

import androidx.lifecycle.LiveData
import com.daredevil.mvitest.util.ApiResponse
import com.daredevil.twitchauth.model.Token
import retrofit2.http.*

interface OAuthApiService {

    @POST("token")
    @FormUrlEncoded
    fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String,
        @Field("grant_type") grantType: String = "authorization_code",
        @Field("redirect_uri") redirectUri: String
    ): LiveData<ApiResponse<Token>>
}