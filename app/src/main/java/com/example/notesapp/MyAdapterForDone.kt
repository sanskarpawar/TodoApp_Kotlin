package com.example.notesapp

import android.provider.Settings.Global.getString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.realtimedatabasekotlin.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MyAdapterForDone(private val userList: ArrayList<User>) : RecyclerView.Adapter<MyAdapterForDone.MyViewHolder>()
{

    private lateinit var database : DatabaseReference
    private lateinit var databasedone : DatabaseReference


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(
            R.layout.single_todo_item,
            parent, false
        )

        return MyViewHolder(itemview)

    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = userList[position]
        holder.titleOfNote.text = currentitem.edtTitleOfNote
        val idForNote = userList[position].idForNote
      database = FirebaseDatabase.getInstance().getReference(Constants.ROOT_NODE_TODO)
        databasedone = FirebaseDatabase.getInstance().getReference(Constants.COMPLETED)

        holder.cardviewitem.setBackgroundResource(R.color. cardcolor)
        holder.donetask.setImageResource(R.drawable.donetask)
        holder.deletebtn.setOnClickListener {
            idForNote?.let { it1 ->
                userList.clear()
                databasedone.child(it1).removeValue();


            }
        }

    }
    override fun getItemCount(): Int {
        return userList.size
    }
    class MyViewHolder(itemview: View): RecyclerView.ViewHolder(itemview)
    {
        val titleOfNote : TextView = itemview.findViewById(R.id.txtNoteTitle)
        val donetask : ImageView = itemview.findViewById(R.id.imgDone)
        val cardviewitem : CardView = itemview.findViewById(R.id.carditem)
        val deletebtn : Button = itemview.findViewById(R.id.btnDelete)
    }
}