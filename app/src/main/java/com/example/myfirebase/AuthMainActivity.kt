package com.example.myfirebase

import android.app.Activity
import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
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
//    var checkEmail = false
//    val checkPassword = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // binding.SignInBtn.isEnabled = false

        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this,gso)

        binding.GoogleSignBtn.setOnClickListener(View.OnClickListener {
            signInGoogle()
        })


//        authbinding.EmailIDEdittext.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//
//            override fun afterTextChanged(s: Editable?) {
//                val email_String = s.toString()
//                if(email_String.isEmpty()){
//                    authbinding.layoutEmailEdittext.setBackgroundResource(R.drawable.border_line_error_edittext)
//                    authbinding.EmailErrorText.text = "Email Cannot Be Empty"
//                    checkEmail = false
//                }else if (!Patterns.EMAIL_ADDRESS.matcher(email_String).matches()){
//                    authbinding.layoutEmailEdittext.setBackgroundResource(R.drawable.border_line_error_edittext)
//                    authbinding.EmailErrorText.text = "Invalid Email-ID"
//                    checkEmail = false
//                }else{
//                    authbinding.layoutEmailEdittext.setBackgroundResource(R.drawable.border_line_error_edittext)
//                    authbinding.EmailErrorText.text = null
//                    checkEmail = true
//                }
//
//                authbinding.SignInBtn.isEnabled = checkEmail == true
//            }
//        })

//        authbinding.SignInBtn.setOnClickListener(View.OnClickListener {
//            val intent = Intent(this,WelcomeMainActivity::class.java)
//            startActivity(intent)
//        })

    }

    // Email And Password Authentication







    override fun onStart() {
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intentUser = Intent(this,WelcomeMainActivity::class.java)
            intentUser.putExtra("name",currentUser.displayName)
            intentUser.putExtra("email",currentUser.email)
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




