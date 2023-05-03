package com.example.myfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfirebase.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private lateinit var databaseReference: DatabaseReference
    private lateinit var newDatabaseReference: DatabaseReference
    private lateinit var adapter: MyAdapter
    private lateinit var userList: ArrayList<MyDataModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SaveDataBtn.setOnClickListener(View.OnClickListener {
            sendData()
        })

        //binding.userRecyclerView.layoutManager = LinearLayoutManager(this)

        newDatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

//        newDatabaseReference.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//               if (snapshot.exists()) {
//                   for(item in snapshot.children){
//                       val name = item.child("name").value.toString()
//                       val surname = item.child("surname").value.toString()
//                       val age = item.child("age").value.toString()
//                       val gender = item.child("gender").value.toString()
//                       val username = item.child("username").value.toString()
//                       userList.add(MyDataModel(name,surname, age, gender, username))
//                   }
//                   val UserAdapter = MyAdapter(userList)
//                   binding.userRecyclerView.adapter = UserAdapter
////                   adapter.notifyDataSetChanged()
//               }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        })


    }

    private fun sendData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        val User_Name = binding.EditTextName.text.toString()
        val User_SurName = binding.EditTextSurName.text.toString()
        val User_Age = binding.EditTextAge.text.toString()
        val User_Gender = binding.EditTextGender.text.toString()
        val User_Username = binding.EditTextUserName.text.toString()
        val id = databaseReference.push().key

//        if(  ( (User_Name.isEmpty() && User_SurName.isEmpty() )  &&  (User_Age.isEmpty() && User_Gender.isEmpty()) )  && User_Username.isEmpty()   )
//        {
//            Toast.makeText(applicationContext,"Please Enter The Whole Info",Toast.LENGTH_SHORT).show()
//        }else{
//            val data = MyDataModel(User_Name,User_SurName,User_Age,User_Gender,User_Username)
//
//            val UserRef = databaseReference.child(User_Username)
//                UserRef.addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    if (snapshot.exists()) {
//                        Toast.makeText(applicationContext, "Username Already Exits", Toast.LENGTH_SHORT).show()
//                        binding.EditTextUserName.text.clear()
//                    } else{
//
//                        databaseReference.child(User_Username).setValue(data).addOnSuccessListener {
//                            binding.EditTextName.text.clear()
//                            binding.EditTextSurName.text.clear()
//                            binding.EditTextAge.text.clear()
//                            binding.EditTextGender.text.clear()
//                            binding.EditTextUserName.text.clear()
//
//                            Toast.makeText(applicationContext, "Data Saved Successfully", Toast.LENGTH_SHORT).show()
//                        }.addOnFailureListener {
//                            Toast.makeText(applicationContext, "Failed To Save Data", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//                    override fun onCancelled(error: DatabaseError) {}
//            })
//            }

    }
}


