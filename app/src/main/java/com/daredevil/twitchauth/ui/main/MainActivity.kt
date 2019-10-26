package com.daredevil.twitchauth.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.daredevil.twitchauth.R
import com.daredevil.twitchauth.util.Constants
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login_button.setOnClickListener { view ->
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://id.twitch.tv/oauth2/authorize?client_id=${Constants.CLIENT_ID}&redirect_uri=${Constants.REDIRECT_URI}&response_type=code&scope=${Constants.SCOPE}")
            )
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        val uri = intent.data

        if (uri != null && uri.toString().startsWith(Constants.REDIRECT_URI)) {
            println("DEBUG: $uri")
            getAccessToken(uri.getQueryParameter("code") ?: "")
        }
    }

    private fun getAccessToken(code: String) {
        mainViewModel.getAccessToken(
            clientId = Constants.CLIENT_ID,
            clientSecret = Constants.CLIENT_SECRET,
            code = code,
            redirectUri = Constants.REDIRECT_URI
        ).observe(this, Observer { resource ->
            resource.data?.let { token ->
                println("DEBUG: $token")
                getUser(token.accessToken)
            }

            resource.message?.let { message ->
                println("DEBUG: $message")
            }
        })
    }

    private fun getUser(accessToken: String) {
        mainViewModel.getUser(accessToken = accessToken, clientId = Constants.CLIENT_ID)
            .observe(this, Observer { resource ->
                resource.data?.let { user ->
                    println("DEBUG: $user")
                    Toast.makeText(this, user.toString(), Toast.LENGTH_LONG).show()
                }

                resource.message?.let { message ->
                    println("DEBUG: $message")
                }
            })
    }
}
