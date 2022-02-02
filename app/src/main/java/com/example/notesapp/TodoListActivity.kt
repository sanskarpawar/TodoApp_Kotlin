package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.MainActivityBinding
import com.example.realtimedatabasekotlin.User
import com.google.firebase.database.*
import kotlin.math.log

class TodoListActivity : AppCompatActivity() {

   // private lateinit var binding : MainActivityBinding
    private lateinit var database : DatabaseReference
    private lateinit var  userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<User>
    private lateinit var loopthing : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)

        userRecyclerView =findViewById(R.id.todoList)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf<User>()
        getUserData()
        //Log.d( loopthing ,"Snapshot Exist or not")


    }

    private fun getUserData() {
        database = FirebaseDatabase.getInstance().getReference("Todo")
        //Log.d( loopthing ,"Snapshot Exist or not")
       database.addValueEventListener(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //Log.d( loopthing ,"Snapshot Exist or not"+snapshot.exists())

                if(snapshot.exists()){

                    for (userSnapshot in snapshot.children)
                    {
                        val user = userSnapshot.getValue(User::class.java)
                        userArrayList.add(user!!)

                        Log.d( loopthing ,"For Loop ")
                    }
                    userRecyclerView.adapter =MyAdapter(userArrayList)


                }


            }

            override fun onCancelled(error: DatabaseError) {

            }


        })

      //  userRecyclerView.sta
    }
}
