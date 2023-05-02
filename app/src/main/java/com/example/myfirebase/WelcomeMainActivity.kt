package com.example.myfirebase

import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myfirebase.databinding.ActivityWelcomeMainBinding
import com.google.firebase.auth.FirebaseAuth

class WelcomeMainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWelcomeMainBinding
    private lateinit var Auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Auth = FirebaseAuth.getInstance()

        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")

        binding.DisplayName.text = name
        binding.Email.text = email


        binding.GoogleSignOutBtn.setOnClickListener {
            Auth.signOut()
            startActivity(Intent(this, AuthMainActivity::class.java))
        }

    }
}














//startActivity(Intent(this, AuthMainActivity::class.java))
//val activityManager = getSystemService(android.content.Context.ACTIVITY_SERVICE) as ActivityManager
//activityManager.clearApplicationUserData()