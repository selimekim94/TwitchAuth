package com.daredevil.twitchauth.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.daredevil.twitchauth.repository.OAuthRepository
import com.daredevil.twitchauth.model.Token
import com.daredevil.twitchauth.model.User
import com.daredevil.twitchauth.repository.TwitchRepository
import com.daredevil.twitchauth.util.Constants
import com.daredevil.twitchauth.util.Resource
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val oAuthRepository: OAuthRepository,
    private val twitchRepository: TwitchRepository
) :
    ViewModel() {

    private val _code = MutableLiveData<String>()
    private val _accessToken = MutableLiveData<String>()

    val code: LiveData<String>
        get() = _code

    val accessToken: LiveData<String>
        get() = _accessToken

    val login: LiveData<Resource<Token>> = Transformations.switchMap(_code) { code ->
        oAuthRepository.getAccessToken(
            clientId = Constants.CLIENT_ID,
            clientSecret = Constants.CLIENT_SECRET,
            code = code,
            redirectUri = Constants.REDIRECT_URI
        )
    }

    val user: LiveData<Resource<User>> = Transformations.switchMap(_accessToken) { accessToken ->
        twitchRepository.getUser(
            accessToken = accessToken,
            clientId = Constants.CLIENT_ID
        )
    }

    fun setCode(code: String?) {
        _code.value = code
    }

    fun setAccessToken(accessToken: String) {
        _accessToken.value = accessToken
    }
}
