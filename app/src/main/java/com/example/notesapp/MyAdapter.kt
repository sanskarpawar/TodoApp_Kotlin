package com.example.notesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.realtimedatabasekotlin.User

class MyAdapter(private val userList : ArrayList<User>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.single_todo_item,
        parent,false)
        return MyViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = userList[position]
        holder.edtTitleOfNote.text = currentitem.edtTitleOfNote

    }

    override fun getItemCount(): Int {
        return userList.size
    }



    class MyViewHolder(itemview:View): RecyclerView.ViewHolder(itemview)
    {
        val edtTitleOfNote : TextView = itemview.findViewById(R.id.txtNoteTitle)
    }
}