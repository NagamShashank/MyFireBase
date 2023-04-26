package com.example.myfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.myfirebase.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SendDataBtn.setOnClickListener(View.OnClickListener {
            sendData()
        })

    }

    private fun sendData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        val User_Name = binding.EditTextName.text.toString()
        val User_SurName = binding.EditTextSurName.text.toString()
        val id = databaseReference.push().key

        if(User_Name.isEmpty()&&User_SurName.isEmpty()){
            Toast.makeText(applicationContext,"Please Enter The Whole Info",Toast.LENGTH_SHORT).show()
        }else{
            val data = MyDataModel(User_Name,User_SurName)
            if (id != null) {
                databaseReference.child(id).setValue(data).addOnSuccessListener {
                    binding.EditTextName.text.clear()
                    binding.EditTextSurName.text.clear()

                    Toast.makeText(applicationContext,"Data Saved Successfully",Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(applicationContext,"Failed To Saved",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}


