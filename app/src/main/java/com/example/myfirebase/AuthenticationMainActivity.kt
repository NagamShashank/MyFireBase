package com.example.myfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myfirebase.databinding.ActivityAuthenticationMainBinding

class AuthenticationMainActivity : AppCompatActivity() {

    private lateinit var Authbinding : ActivityAuthenticationMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Authbinding = ActivityAuthenticationMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_authentication_main)


    }
}