package com.daredevil.twitchauth.repository

import androidx.lifecycle.LiveData
import com.daredevil.twitchauth.db.TokenDao
import com.daredevil.mvitest.util.ApiResponse
import com.daredevil.twitchauth.api.OAuthApiService
import com.daredevil.twitchauth.model.Token
import com.daredevil.twitchauth.util.Resource
import javax.inject.Inject

class OAuthRepository @Inject constructor(
    private val tokenDao: TokenDao,
    private val apiService: OAuthApiService
) {

    fun getAccessToken(
        clientId: String,
        clientSecret: String,
        code: String,
        redirectUri: String
    ): LiveData<Resource<Token>> {
        return object : NetworkBoundResource<Token, Token>() {
            override fun saveCallResult(item: Token) {
                tokenDao.insertToken(item)
            }

            override fun shouldFetch(data: Token?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<Token> {
                return tokenDao.getToken("1")
            }

            override fun createCall(): LiveData<ApiResponse<Token>> {
                return apiService.getAccessToken(
                    clientId = clientId,
                    clientSecret = clientSecret,
                    code = code,
                    redirectUri = redirectUri
                )
            }

        }.asLiveData()

    }
}