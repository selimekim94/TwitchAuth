package com.daredevil.twitchauth.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.daredevil.twitchauth.repository.OAuthRepository
import com.daredevil.twitchauth.model.Token
import com.daredevil.twitchauth.model.User
import com.daredevil.twitchauth.repository.TwitchRepository
import com.daredevil.twitchauth.util.Resource
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val oAuthRepository: OAuthRepository,
    private val twitchRepository: TwitchRepository
) :
    ViewModel() {

    fun getAccessToken(
        clientId: String,
        clientSecret: String,
        code: String,
        redirectUri: String
    ): LiveData<Resource<Token>> {
        return oAuthRepository.getAccessToken(
            clientId = clientId,
            clientSecret = clientSecret,
            code = code,
            redirectUri = redirectUri
        )
    }

    fun getUser(
        accessToken: String,
        clientId: String
    ): LiveData<Resource<User>> {
        return twitchRepository.getUser(
            accessToken = accessToken,
            clientId = clientId
        )
    }
}