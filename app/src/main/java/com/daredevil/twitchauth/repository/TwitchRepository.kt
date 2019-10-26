package com.daredevil.twitchauth.repository

import androidx.lifecycle.LiveData
import com.daredevil.mvitest.util.ApiResponse
import com.daredevil.twitchauth.api.TwitchApiService
import com.daredevil.twitchauth.db.UserDao
import com.daredevil.twitchauth.model.User
import com.daredevil.twitchauth.model.UserResponse
import com.daredevil.twitchauth.util.Resource
import javax.inject.Inject

class TwitchRepository @Inject constructor(
    private val userDao: UserDao,
    private val apiService: TwitchApiService
) {
    fun getUser(
        accessToken: String,
        clientId: String
    ): LiveData<Resource<User>> {
        return object : NetworkBoundResource<User, UserResponse>() {
            override fun saveCallResult(item: UserResponse) {
                userDao.insertUser(item.data[0])
            }

            override fun shouldFetch(data: User?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<User> {
                return userDao.getUser()
            }

            override fun createCall(): LiveData<ApiResponse<UserResponse>> {
                return apiService.getUser(
                    authorization = "Bearer $accessToken",
                    clientId = clientId
                )
            }

        }.asLiveData()
    }

}