package com.daredevil.twitchauth.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @Expose
    @SerializedName("data")
    val data: List<User>
)