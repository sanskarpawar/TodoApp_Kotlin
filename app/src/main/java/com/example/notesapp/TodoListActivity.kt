package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.realtimedatabasekotlin.User
import com.google.firebase.database.*
import kotlin.collections.ArrayList

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


        val fab: View = findViewById(R.id.addfloatingBtn)
        //val donetask: ImageView = findViewById(R.id.imgDone)
        userRecyclerView.layoutManager = LinearLayoutManager(this)

        //database = FirebaseDatabase.getInstance().getReference(getString(R.string.databaseRefTodo))
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()
        getUserData()

        fab.setOnClickListener {

            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
        /*donetask.setOnClickListener {

        }*/

          //  val recyclerView = findViewById<RecyclerView>(R.id.todoList)




    }

    private fun getUserData() {
        database = FirebaseDatabase.getInstance().getReference(getString(R.string.databaseRefTodo))


       database.addValueEventListener(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                    userArrayList.clear()
                if(snapshot.exists()){

                    for (userSnapshot in snapshot.children)
                    {
                        val user = userSnapshot.getValue(User::class.java)
                        userArrayList.add(user!!)

                    }
                    val adapter = MyAdapter(userArrayList)
                    userRecyclerView.adapter = adapter
                    adapter.setOnItemClickListener(object :MyAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                          val idForNote = userArrayList[position].idForNote
                            val edtTitleOfNote = userArrayList[position].edtTitleOfNote
                           val edtNoteDiscripton = userArrayList[position].edtNoteDiscripton
                            val intent = Intent(this@TodoListActivity,MainActivity::class.java)
                            intent.putExtra("noteType", "Edit")
                            intent.putExtra(getString(R.string.titleoftask), edtTitleOfNote)
                            intent.putExtra(getString(R.string.discriptionoftask),edtNoteDiscripton)
                            intent.putExtra(getString(R.string.uniqueIdForTask),idForNote)
                            Log.d("idofnote", "$idForNote" )
                            Log.d("noteTitle", "$edtTitleOfNote" )
                            startActivity(intent)

                            Toast.makeText(this@TodoListActivity,"you Clicked on $edtTitleOfNote ",Toast.LENGTH_LONG).show()
                        }


                    }){}















                }


            }

            override fun onCancelled(error: DatabaseError) {

            }


        })



      //  userRecyclerView.sta
    }
}
































/*  adapter.setOnItemClickListener(object: MyAdapter.onItemClickListener){

                        }

                           val delete = findViewById<Button>(R.id.btnDelete)
                           delete.setOnClickListener {

                               //database.child(todoId.toString()).removeValue().addOnCompleteListener(new onCom)
                               Toast.makeText(this@TodoListActivity,"you Clicked on $todoId ",Toast.LENGTH_LONG).show()

                           }*/