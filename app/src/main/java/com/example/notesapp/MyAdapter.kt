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


class MyAdapter(private val userList: ArrayList<User>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>()
{
    private lateinit var mListener : onItemClickListener
    private lateinit var database : DatabaseReference
    private lateinit var databasedone : DatabaseReference

    interface onItemClickListener{

        fun onItemClick(position : Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener, function: () -> Unit){

        mListener = listener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(
            R.layout.single_todo_item,
            parent, false
        )

        return MyViewHolder(itemview,mListener)

    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = userList[position]
        holder.titleOfNote.text = currentitem.edtTitleOfNote

        val title = userList[position].edtTitleOfNote
        val done= userList[position].doneOrNot
        val idForNote = userList[position].idForNote
        val discription = userList[position].edtNoteDiscripton
        database = FirebaseDatabase.getInstance().getReference(Constants.ROOT_NODE_TODO)
        databasedone = FirebaseDatabase.getInstance().getReference("Completed")
        holder.donetask.setOnClickListener {


            if (idForNote != null)
            { if (done != null) {
                if (title != null){
                    if (discription != null){

                        val User = User(title, discription,idForNote,"done")

                        database.child(idForNote).child(Constants.TASK_DONE_OR_NOT).setValue(Constants.DONE_TEXT)
                        userList.clear()
                        databasedone.child(idForNote).setValue(User)
                        database.child(idForNote).removeValue();




                }}}}

        }

        holder.deletebtn.setOnClickListener {
            idForNote?.let { it1 ->
                userList.clear()
                database.child(it1).removeValue();


                }
            }



        /*Log.d(Constants.DONE_TEXT,"$done")
        if(done==Constants.DONE_TEXT)
        {
            holder.cardviewitem.setBackgroundResource(R.color. cardcolor)

            holder.donetask.setImageResource(R.drawable.donetask)


            //holder.absoluteAdapterPosition

        }*/



    }
    override fun getItemCount(): Int {
        return userList.size
    }
    class MyViewHolder(itemview: View,listener: onItemClickListener): RecyclerView.ViewHolder(itemview)
    {
        val titleOfNote : TextView = itemview.findViewById(R.id.txtNoteTitle)
        val donetask : ImageView = itemview.findViewById(R.id.imgDone)

        val cardviewitem : CardView = itemview.findViewById(R.id.carditem)
        val deletebtn : Button = itemview.findViewById(R.id.btnDelete)

        init {

            itemView.setOnClickListener {
                listener.onItemClick(bindingAdapterPosition)

            }


        }





    }
}