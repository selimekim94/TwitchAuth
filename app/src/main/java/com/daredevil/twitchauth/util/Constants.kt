package com.daredevil.twitchauth.util

object Constants {
    const val OAUTH_ENDPOINT = "https://id.twitch.tv/oauth2/"
    const val TWITCH_ENDPOINT = "https://api.twitch.tv/helix/"

    const val CLIENT_ID: String = "4pbutiyio7m1n5sfg2bpcsgzrw24qm"
    const val CLIENT_SECRET: String = "y22wybgwi39d9vlokizue7zwtettbm"
    const val REDIRECT_URI: String = "daredevil://callback"
    const val SCOPE: String = "user:edit+user:read:email+user_read"
}