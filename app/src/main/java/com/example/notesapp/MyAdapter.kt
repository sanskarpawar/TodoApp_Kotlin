package com.example.notesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.realtimedatabasekotlin.User
import org.w3c.dom.Text

class MyAdapter(private val userList: ArrayList<User>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>()
{
    private lateinit var mListener : onItemClickListener

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



    }
    override fun getItemCount(): Int {
        return userList.size
    }
    class MyViewHolder(itemview: View,listener: onItemClickListener): RecyclerView.ViewHolder(itemview)
    {
        val titleOfNote : TextView = itemview.findViewById(R.id.txtNoteTitle)


        //val timeStamp : TextView =itemview.findViewById(R.id.txttimestamp)
      // val cardviewitem : CardView = itemview.findViewById(R.id.carditem)
        init {

            itemView.setOnClickListener {

                listener.onItemClick(bindingAdapterPosition)

            }


        }





    }
}