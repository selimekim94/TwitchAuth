package com.daredevil.twitchauth.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    @Expose
    @SerializedName("id")
    @ColumnInfo(name = "id")
    val id: String,

    @Expose
    @SerializedName("login")
    @ColumnInfo(name = "login")
    val login: String,

    @Expose
    @SerializedName("display_name")
    @ColumnInfo(name = "display_name")
    val display_name: String,

    @Expose
    @SerializedName("type")
    @ColumnInfo(name = "type")
    val type: String,

    @Expose
    @SerializedName("broadcaster_type")
    @ColumnInfo(name = "broadcaster_type")
    val broadcaster_type: String,

    @Expose
    @SerializedName("description")
    @ColumnInfo(name = "description")
    val description: String,

    @Expose
    @SerializedName("profile_image_url")
    @ColumnInfo(name = "profile_image_url")
    val profile_image_url: String,

    @Expose
    @SerializedName("offline_image_url")
    @ColumnInfo(name = "offline_image_url")
    val offline_image_url: String,

    @Expose
    @SerializedName("view_count")
    @ColumnInfo(name = "view_count")
    val view_count: String,

    @Expose
    @SerializedName("email")
    @ColumnInfo(name = "email")
    val email: String
) {
    override fun toString(): String {
        return "User(id='$id', login='$login', display_name='$display_name', type='$type', broadcaster_type='$broadcaster_type', description='$description', profile_image_url='$profile_image_url', offline_image_url='$offline_image_url', view_count='$view_count', email='$email')"
    }
}