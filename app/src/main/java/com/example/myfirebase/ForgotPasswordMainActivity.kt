package com.example.myfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import com.example.myfirebase.databinding.ActivityForgotPasswordMainBinding

class ForgotPasswordMainActivity : AppCompatActivity() {

    private lateinit var FPbinding : ActivityForgotPasswordMainBinding

    var newPassToggle = false
    var confirmPassToggle = false

    var checkNewPassword = false
    var checkConfirmPassword = false

    var PasswordNew : String = ""
    var PasswordConfrim : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FPbinding = ActivityForgotPasswordMainBinding.inflate(layoutInflater)
        setContentView(FPbinding.root)

        FPbinding.SavePasswordButton.isEnabled = false

        FPbinding.NewPasswordToggle.setOnClickListener(View.OnClickListener {
            if(newPassToggle){
                FPbinding.NewPasswordEdittext.transformationMethod = PasswordTransformationMethod.getInstance()
                FPbinding.NewPasswordToggle.setBackgroundResource(R.drawable.password_invisible)
                newPassToggle = false
            }else{
                FPbinding.NewPasswordEdittext.transformationMethod = HideReturnsTransformationMethod.getInstance()
                FPbinding.NewPasswordToggle.setBackgroundResource(R.drawable.password_visible)
                newPassToggle = true
            }
        })

        FPbinding.ConfirmPasswordToggle.setOnClickListener(View.OnClickListener {
            if(confirmPassToggle){
                FPbinding.ConfirmPasswordEdittext.transformationMethod = PasswordTransformationMethod.getInstance()
                FPbinding.ConfirmPasswordToggle.setBackgroundResource(R.drawable.password_invisible)
                confirmPassToggle = false
            }else{
                FPbinding.ConfirmPasswordEdittext.transformationMethod = HideReturnsTransformationMethod.getInstance()
                FPbinding.ConfirmPasswordToggle.setBackgroundResource(R.drawable.password_visible)
                confirmPassToggle = true
            }
        })

        FPbinding.NewPasswordEdittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val newPassword = s.toString()

                if(newPassword.isEmpty()){
                    FPbinding.NewPasswordErrorText.text = "Password cannot be Empty"
                    FPbinding.layoutNewPasswordEdittext.setBackgroundResource(R.drawable.border_line_error_edittext)
                    checkNewPassword = false
                }else if(!newPassword.matches(".*[a-z].*".toRegex())){
                    FPbinding.NewPasswordErrorText.text = "Atleast One Lowercase is needed"
                    FPbinding.layoutNewPasswordEdittext.setBackgroundResource(R.drawable.border_line_error_edittext)
                    checkNewPassword = false
                }else if(!newPassword.matches(".*[A-Z].*".toRegex())){
                    FPbinding.NewPasswordErrorText.text = "Atleast One Uppercase is needed"
                    FPbinding.layoutNewPasswordEdittext.setBackgroundResource(R.drawable.border_line_error_edittext)
                    checkNewPassword = false
                }else if(!newPassword.matches(".*[0-9].*".toRegex())){
                    FPbinding.NewPasswordErrorText.text = "Atleast One Number is needed"
                    FPbinding.layoutNewPasswordEdittext.setBackgroundResource(R.drawable.border_line_error_edittext)
                    checkNewPassword = false
                }else if(!newPassword.matches(".*[!@#\$%&*_].*".toRegex())){
                    FPbinding.NewPasswordErrorText.text = "Atleast One Special Character is needed"
                    FPbinding.layoutNewPasswordEdittext.setBackgroundResource(R.drawable.border_line_error_edittext)
                    checkNewPassword = false
                }else if(newPassword.length < 8){
                    FPbinding.NewPasswordErrorText.text = "Password is Too Short"
                    FPbinding.layoutNewPasswordEdittext.setBackgroundResource(R.drawable.border_line_error_edittext)
                    checkNewPassword = false
                }else {
                    FPbinding.NewPasswordErrorText.text = null
                    FPbinding.layoutNewPasswordEdittext.setBackgroundResource(R.drawable.border_line_edittext)
                    checkNewPassword = true
                }



                PasswordNew = s.toString()



                FPbinding.SavePasswordButton.isEnabled = (checkNewPassword) && (checkConfirmPassword)

            }
        })


        FPbinding.ConfirmPasswordEdittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val confirmPassword = s.toString()
                if(confirmPassword.isEmpty()){
                    FPbinding.ConfirmPasswordErrorText.text = "Password cannot be empty"
                    FPbinding.layoutConfirmPasswordEdittext.setBackgroundResource(R.drawable.border_line_error_edittext)
                    checkConfirmPassword = false
                }else if(confirmPassword != PasswordNew){
                    FPbinding.ConfirmPasswordErrorText.text = "Password Does not Matches The Above Password Entered"
                    FPbinding.layoutConfirmPasswordEdittext.setBackgroundResource(R.drawable.border_line_error_edittext)
                    checkConfirmPassword = false
                }else{
                    FPbinding.ConfirmPasswordErrorText.text = null
                    FPbinding.layoutConfirmPasswordEdittext.setBackgroundResource(R.drawable.border_line_edittext)
                    checkConfirmPassword = true
                }

                PasswordConfrim = s.toString()

                FPbinding.SavePasswordButton.isEnabled = (checkNewPassword) && (checkConfirmPassword)

            }
        })

        FPbinding.SavePasswordButton.setOnClickListener(View.OnClickListener {
            Toast.makeText(this,"Saved password Successfully ",Toast.LENGTH_SHORT).show()
        })


    }
}