package com.daredevil.twitchauth.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.daredevil.twitchauth.R
import com.daredevil.twitchauth.util.Constants
import com.daredevil.twitchauth.util.Resource
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
                Uri.parse("${Constants.OAUTH_ENDPOINT}authorize?client_id=${Constants.CLIENT_ID}&redirect_uri=${Constants.REDIRECT_URI}&response_type=code&scope=${Constants.SCOPE}")
            )
            startActivity(intent)
        }

        mainViewModel.login
            .observe(this, Observer { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        println("DEBUG: Loading")
                    }

                    is Resource.Success -> {
                        resource.data?.let { token ->
                            println("DEBUG: $token")
                            mainViewModel.setAccessToken(token.accessToken)
                        }
                    }

                    is Resource.Error -> {
                        resource.message?.let { message ->
                            println("DEBUG: $message")
                        }
                    }
                }
            })

        mainViewModel.user
            .observe(this, Observer { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        println("DEBUG: Loading")
                    }

                    is Resource.Success -> {
                        resource.data?.let { user ->
                            println("DEBUG: $user")
                            Toast.makeText(this, user.toString(), Toast.LENGTH_LONG).show()
                        }
                    }

                    is Resource.Error -> {
                        resource.message?.let { message ->
                            println("DEBUG: $message")
                        }
                    }
                }
            })
    }

    override fun onResume() {
        super.onResume()
        val uri = intent.data

        if (uri != null && uri.toString().startsWith(Constants.REDIRECT_URI)) {
            println("DEBUG: $uri")
            mainViewModel.setCode(uri.getQueryParameter("code") ?: "")
        }
    }

}
