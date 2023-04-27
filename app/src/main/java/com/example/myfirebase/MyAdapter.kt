package com.example.myfirebase

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirebase.databinding.UseritemlayoutBinding

class MyAdapter(val UserArrayList:ArrayList<MyDataModel>): RecyclerView.Adapter<MyAdapter.MyView>() {
    class MyView (val useritemlayoutBinding: UseritemlayoutBinding):RecyclerView.ViewHolder(useritemlayoutBinding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyView {
        return MyView(UseritemlayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyAdapter.MyView, position: Int) {
        holder.useritemlayoutBinding.Username.text = UserArrayList[position].username
        holder.useritemlayoutBinding.Name.text = UserArrayList[position].name
        holder.useritemlayoutBinding.Surname.text = UserArrayList[position].surname
        holder.useritemlayoutBinding.Age.text = UserArrayList[position].age
        holder.useritemlayoutBinding.Gender.text = UserArrayList[position].gender
    }

    override fun getItemCount(): Int {
       return UserArrayList.size
    }
}