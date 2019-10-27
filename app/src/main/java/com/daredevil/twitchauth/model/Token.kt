package com.daredevil.twitchauth.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tokens")
data class Token(
    @PrimaryKey
    @ColumnInfo(name = "id", defaultValue = "1")
    val id: String,

    @Expose
    @SerializedName("access_token")
    @ColumnInfo(name = "access_token")
    val accessToken: String,

    @Expose
    @SerializedName("refresh_token")
    @ColumnInfo(name = "refresh_token")
    val refreshToken: String,

    @Expose
    @SerializedName("expires_in")
    @ColumnInfo(name = "expires_in")
    val expiresIn: Long,

    @Expose
    @SerializedName("scope")
    @ColumnInfo(name = "scope")
    val scope: List<String>,

    @Expose
    @SerializedName("token_type")
    @ColumnInfo(name = "token_type")
    val tokenType: String
) {
    override fun toString(): String {
        return "Token(id=$id, accessToken='$accessToken', refreshToken='$refreshToken', expiresIn=$expiresIn, scope=$scope, tokenType='$tokenType')"
    }
}