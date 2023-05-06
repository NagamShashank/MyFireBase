package com.example.myfirebase

import android.app.Activity
import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myfirebase.databinding.ActivityAuthMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class AuthMainActivity : AppCompatActivity() {



    private lateinit var auth : FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var binding : ActivityAuthMainBinding
    var checkEmail = false
    var checkPassword = false
    var password = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()



        binding.signUpButton.isEnabled = false

        binding.SignInText.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,AuthMainActivity2::class.java)
            startActivity(intent)
        })


        binding.PasswordToggle.setOnClickListener(View.OnClickListener {
            if(password){
                binding.PasswordEdittext.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.PasswordToggle.setBackgroundResource(R.drawable.password_invisible)
                password = false
            }else{
                binding.PasswordEdittext.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.PasswordToggle.setBackgroundResource(R.drawable.password_visible)
                password = true
            }
        })

        binding.EmailIDEdittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val email_String = s.toString()
                if(email_String.isEmpty()){
                    binding.layoutEmailEdittext.setBackgroundResource(R.drawable.border_line_error_edittext)
                    binding.EmailErrorText.text = "Email Cannot Be Empty"
                    checkEmail = false
                }else if (!Patterns.EMAIL_ADDRESS.matcher(email_String).matches()){
                    binding.layoutEmailEdittext.setBackgroundResource(R.drawable.border_line_error_edittext)
                    binding.EmailErrorText.text = "Invalid Email-ID"
                    checkEmail = false
                }else{
                    binding.layoutEmailEdittext.setBackgroundResource(R.drawable.border_line_edittext)
                    binding.EmailErrorText.text = null
                    checkEmail = true
                }

                binding.signUpButton.isEnabled = (checkEmail)&&(checkPassword)
            }
        })

        binding.PasswordEdittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val user_Password = s.toString()
                if(user_Password.isEmpty()){
                    binding.PasswordErrorText.text = "Password Cannot be Empty"
                    binding.layoutPasswordEdittext.setBackgroundResource(R.drawable.border_line_error_edittext)
                    checkPassword = false
                }else if(!user_Password.matches(".*[a-z].*".toRegex())){
                    binding.PasswordErrorText.text = "Atleast one Lowercase is required"
                    binding.layoutPasswordEdittext.setBackgroundResource(R.drawable.border_line_error_edittext)
                    checkPassword = false
                }else if(!user_Password.matches(".*[A-Z].*".toRegex())){
                    binding.PasswordErrorText.text = "Atleast one Uppercase is required"
                    binding.layoutPasswordEdittext.setBackgroundResource(R.drawable.border_line_error_edittext)
                    checkPassword = false
                }else if(!user_Password.matches(".*[0-9].*".toRegex())){
                    binding.PasswordErrorText.text = "Atleast one Number is required"
                    binding.layoutPasswordEdittext.setBackgroundResource(R.drawable.border_line_error_edittext)
                    checkPassword = false
                }else if(!user_Password.matches(".*[!@#$%&*_].*".toRegex())){
                    binding.PasswordErrorText.text = "Atleast one Special Character is required"
                    binding.layoutPasswordEdittext.setBackgroundResource(R.drawable.border_line_error_edittext)
                    checkPassword = false
                }else if (user_Password.length < 8){
                    binding.PasswordErrorText.text = "Password Length too small"
                    binding.layoutPasswordEdittext.setBackgroundResource(R.drawable.border_line_error_edittext)
                    checkPassword = false
                }else{
                    binding.PasswordErrorText.text = null
                    binding.layoutPasswordEdittext.setBackgroundResource(R.drawable.border_line_edittext)
                    checkPassword = true
                }


                    binding.signUpButton.isEnabled = (checkEmail)&&(checkPassword)

            }
        })

        binding.signUpButton.setOnClickListener(View.OnClickListener {
//            val intent = Intent(this,WelcomeMainActivity::class.java)
//            startActivity(intent)
            //Toast.makeText(applicationContext,"Validation Checked Successfully",Toast.LENGTH_SHORT).show()

            val User_Email = binding.EmailIDEdittext.text.toString()
            val User_Password = binding.PasswordEdittext.text.toString()

            auth.createUserWithEmailAndPassword(User_Email,User_Password)
                .addOnCompleteListener(this){task ->
                    if(task.isSuccessful){
                        Toast.makeText(applicationContext,"Sign Up Successfully",Toast.LENGTH_SHORT).show()
                        val SignUpIntent = Intent(this,WelcomeMainActivity::class.java)
                        SignUpIntent.putExtra("name","XYZ")
                        SignUpIntent.putExtra("email",User_Email)
                        startActivity(SignUpIntent)
                        finish()
                    }else{
                        Toast.makeText(applicationContext,"Failed To Sign Up ",Toast.LENGTH_SHORT).show()
                    }
                }

        })





        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this,gso)

        binding.GoogleSignBtn.setOnClickListener(View.OnClickListener {
            signInGoogle()
        })

    }

    // Email And Password Authentication



    override fun onStart() {
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intentUser = Intent(this,WelcomeMainActivity::class.java)
            intentUser.putExtra("name",currentUser.displayName)
            intentUser.putExtra("email",currentUser.email)
            //intentUser.putExtra("password",currentUser.uid)
            startActivity(intentUser)
            finish()
        }
        super.onStart()
    }


    // Google Authentication....
    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
                if(result.resultCode == Activity.RESULT_OK){
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    handleResults(task)
                }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>){
        if (task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if(account != null){
                UpdateUI(account)
            }else{
                Toast.makeText(applicationContext,task.exception.toString(),Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun UpdateUI(account: GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(account.idToken,null)
        auth.signInWithCredential(credential).addOnCompleteListener{
            if(it.isSuccessful){
                val intent = Intent(this,WelcomeMainActivity::class.java)
                intent.putExtra("email",account.email)
                intent.putExtra("name",account.displayName)
                startActivity(intent)
            }else{
                Toast.makeText(applicationContext,it.exception.toString(),Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {

        val activityManager = getSystemService(android.content.Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.clearApplicationUserData()

        super.onBackPressed()
    }
}




