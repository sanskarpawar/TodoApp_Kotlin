package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.databinding.MainActivityBinding
import com.example.realtimedatabasekotlin.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.single_todo_item.view.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding : MainActivityBinding
    private lateinit var database : DatabaseReference

        //val id  String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)




        getSupportActionBar()?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        getSupportActionBar()?.setCustomView(R.layout.abs);

        binding.showList.setOnClickListener {
            intent = Intent(applicationContext, TodoListActivity::class.java)
            startActivity(intent)
        }



        database = FirebaseDatabase.getInstance().getReference(getString(R.string.databaseRefTodo))


        val noteidp = intent.getStringExtra(getString(R.string.uniqueIdForTask))

        val noteType=intent.getStringExtra(getString(R.string.notetype))

        if(noteType.equals(getString(R.string.edit))){
            Log.d("noteType", "$noteType" )
            val noteTitle=intent.getStringExtra(Constants.TITLE_OF_TASK)
            val noteDesc=intent.getStringExtra(Constants.DISCRIPTION_OF_TASK)
            val noteid = intent.getStringExtra(Constants.ID_OF_TASK)
            Log.d("idofnote", "$noteid" )
            Log.d("noteTitle", "$noteTitle" )
            enterButton.text = Constants.UPDATE_BUTTON_TEXT
            edtTitleOfNote.setText(noteTitle)
            edtNoteDiscripton.setText(noteDesc)

            //Delete functionality
            if(noteType.equals(Constants.EDIT))
            {
                binding.btnDelete.visibility= View.VISIBLE
                binding.btnDelete.setOnClickListener {
                    noteidp?.let { it1 ->
                        database.child(it1).removeValue().addOnSuccessListener {
                            edtTitleOfNote.text.clear()
                            edtNoteDiscripton.text.clear()


                            Toast.makeText(this, Constants.TASK_DELETED_MSG, Toast.LENGTH_SHORT).show()

                        }.addOnFailureListener {
                            Toast.makeText(this, Constants.FAILED_DELETED_MSG, Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }

        }
        else
        {
            enterButton.text = Constants.ENTER_BUTTON_TEXT

        }

        binding.enterButton.setOnClickListener {

            val titleofnote = binding.edtTitleOfNote.text.toString()
            val discription = binding.edtNoteDiscripton.text.toString()
            val idForNote = UUID.randomUUID().toString()
            val doneNot = Constants.NOT_DONE_TEXT


            //val timetext = ServerValue.TIMESTAMP.toString()

            database = FirebaseDatabase.getInstance().getReference(Constants.ROOT_NODE_TODO)

            val User = User(titleofnote, discription,idForNote,doneNot)
            if(!noteType.equals(Constants.EDIT)) {

                if (!TextUtils.isEmpty(titleofnote) && !TextUtils.isEmpty(discription)) {

                    database.child(idForNote).setValue(User).addOnSuccessListener {
                        binding.edtTitleOfNote.text.clear()
                        binding.edtNoteDiscripton.text.clear()

                        //database.child(idForNote).child("timestamp").setValue(ServerValue.TIMESTAMP)

                        Toast.makeText(this, Constants.TASK_SAVED_MSG, Toast.LENGTH_SHORT).show()

                    }.addOnFailureListener {

                        Toast.makeText(this, Constants.FAILED_SAVE_MSG, Toast.LENGTH_SHORT).show()


                    }


                } else
                    Toast.makeText(this, Constants.FILL_EMPTY_MSG, Toast.LENGTH_SHORT).show()
            }
            else
            {
                val note= mapOf(
                    Constants.TITLE_OF_TASK to titleofnote,
                    Constants.DISCRIPTION_OF_TASK to discription,
                    Constants.ID_OF_TASK to noteidp

                )

                noteidp?.let { it1 ->
                    database.child(it1).updateChildren(note).addOnSuccessListener {
                        edtTitleOfNote.text.clear()
                        edtNoteDiscripton.text.clear()

                        Toast.makeText(this, Constants.TASK_UPDATE_MSG, Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(this, Constants.FAILED_UPDATE_MSG, Toast.LENGTH_SHORT).show()
                    }
                }




            }

        }


    }

    //fun deletefun(){



}
