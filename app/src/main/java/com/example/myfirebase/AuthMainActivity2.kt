package com.example.myfirebase

import android.app.ActivityManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.myfirebase.databinding.ActivityAuthMain2Binding
import com.google.firebase.auth.FirebaseAuth

class AuthMainActivity2 : AppCompatActivity() {

    private lateinit var Authbinding : ActivityAuthMain2Binding
    private lateinit var myAuth : FirebaseAuth

    var emailCheck = false
    var passwordCheck = false
    var passwordTogglecheck = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Authbinding = ActivityAuthMain2Binding.inflate(layoutInflater)
        setContentView(Authbinding.root)
        myAuth = FirebaseAuth.getInstance()

        Authbinding.signInButton.isEnabled = false


        Authbinding.SignUpText.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,AuthMainActivity::class.java)
            startActivity(intent)
        })




        Authbinding.PasswordAuthToggle.setOnClickListener(View.OnClickListener {
            if(passwordTogglecheck){
                Authbinding.PasswordAuthEdittext.transformationMethod = PasswordTransformationMethod.getInstance()
                Authbinding.PasswordAuthToggle.setBackgroundResource(R.drawable.password_invisible)
                passwordTogglecheck = false
            }else{
                Authbinding.PasswordAuthEdittext.transformationMethod = HideReturnsTransformationMethod.getInstance()
                Authbinding.PasswordAuthToggle.setBackgroundResource(R.drawable.password_visible)
                passwordTogglecheck = true
            }
        })

        Authbinding.EmailAuthEdittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val email_user = s.toString()
                if(email_user.isEmpty()){
                    Authbinding.EmailAuthErrorText.text = "Email Cannot Be Empty"
                    Authbinding.EmailEdittextAuthLayout.setBackgroundResource(R.drawable.border_line_error_edittext)
                    emailCheck = false
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email_user).matches()){
                    Authbinding.EmailAuthErrorText.text = "Invalid Email-ID"
                    Authbinding.EmailEdittextAuthLayout.setBackgroundResource(R.drawable.border_line_error_edittext)
                    emailCheck = false
                }else {
                    Authbinding.EmailAuthErrorText.text = null
                    Authbinding.EmailEdittextAuthLayout.setBackgroundResource(R.drawable.border_line_edittext)
                    emailCheck = true
                }

                Authbinding.signInButton.isEnabled = (emailCheck) && (passwordCheck)
            }
        })

        Authbinding.PasswordAuthEdittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val password_user = s.toString()
                if(password_user.isEmpty()){
                    Authbinding.PasswordAuthErrorText.text = "Password Cannot Be Empty"
                    Authbinding.PasswordEdittextAuthLayout.setBackgroundResource(R.drawable.border_line_error_edittext)
                    passwordCheck = false
                }else if(!password_user.matches(".*[a-z].*".toRegex())){
                    Authbinding.PasswordAuthErrorText.text = "AtLeast one Lowercase is Required"
                    Authbinding.PasswordEdittextAuthLayout.setBackgroundResource(R.drawable.border_line_error_edittext)
                    passwordCheck = false
                }else if(!password_user.matches(".*[A-Z].*".toRegex())){
                    Authbinding.PasswordAuthErrorText.text = "AtLeast one Uppercase is Required"
                    Authbinding.PasswordEdittextAuthLayout.setBackgroundResource(R.drawable.border_line_error_edittext)
                    passwordCheck = false
                }else if (!password_user.matches(".*[0-9].*".toRegex())){
                    Authbinding.PasswordAuthErrorText.text = "AtLeast one Number is Required"
                    Authbinding.PasswordEdittextAuthLayout.setBackgroundResource(R.drawable.border_line_error_edittext)
                    passwordCheck = false
                }else if(!password_user.matches(".*[!@#\$%&*_].*".toRegex())){
                    Authbinding.PasswordAuthErrorText.text = "AtLeast one Special Character is Required"
                    Authbinding.PasswordEdittextAuthLayout.setBackgroundResource(R.drawable.border_line_error_edittext)
                    passwordCheck = false
                }else if(password_user.length < 8){
                    Authbinding.PasswordAuthErrorText.text = "Password Length to Short"
                    Authbinding.PasswordEdittextAuthLayout.setBackgroundResource(R.drawable.border_line_error_edittext)
                    passwordCheck = false
                }else{
                    Authbinding.PasswordAuthErrorText.text = null
                    Authbinding.PasswordEdittextAuthLayout.setBackgroundResource(R.drawable.border_line_edittext)
                    passwordCheck = true
                }

                Authbinding.signInButton.isEnabled = (emailCheck) && (passwordCheck)

            }
        })

        Authbinding.signInButton.setOnClickListener(View.OnClickListener {

            val SignInEmail = Authbinding.EmailAuthEdittext.text.toString()
            val SignInPassword = Authbinding.PasswordAuthEdittext.text.toString()

            myAuth.signInWithEmailAndPassword(SignInEmail,SignInPassword).addOnCompleteListener(this){
                task->
                if(task.isSuccessful){
                    Toast.makeText(applicationContext,"SignIn Successfully", Toast.LENGTH_SHORT).show()
                    val SignInIntent = Intent(this,WelcomeMainActivity::class.java)
                    SignInIntent.putExtra("name","XYZ")
                    SignInIntent.putExtra("email",SignInEmail)
                    startActivity(SignInIntent)
                    finish()
                }else{
                    Toast.makeText(applicationContext,"Failed To SignIn", Toast.LENGTH_SHORT).show()
                }
            }

        })

        Authbinding.UserForgotPassword.setOnClickListener(View.OnClickListener {
            val forgotEmail = Authbinding.EmailAuthEdittext.text.toString()
            val forgotIntent = Intent(this,ForgotPasswordMainActivity::class.java)
            forgotIntent.putExtra("forgotEmail",forgotEmail)
            startActivity(forgotIntent)

        })
    }

    override fun onStart() {
        val usercurrent = myAuth.currentUser
        if(usercurrent != null){
            val userIntent = Intent(this,WelcomeMainActivity::class.java)
            userIntent.putExtra("name",usercurrent.displayName)
            userIntent.putExtra("email",usercurrent.email)
            startActivity(userIntent)
            finish()
        }
        super.onStart()
    }

    override fun onBackPressed() {
        val myactivityManager = getSystemService(android.content.Context.ACTIVITY_SERVICE) as ActivityManager
        myactivityManager.clearApplicationUserData()
        super.onBackPressed()
    }


}