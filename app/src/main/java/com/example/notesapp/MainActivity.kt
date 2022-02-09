package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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

        binding.showList.setOnClickListener {
            intent = Intent(applicationContext, TodoListActivity::class.java)
            startActivity(intent)
        }



        database = FirebaseDatabase.getInstance().getReference(getString(R.string.databaseRefTodo))


        val noteidp = intent.getStringExtra(getString(R.string.uniqueIdForTask))

        val noteType=intent.getStringExtra(getString(R.string.notetype))

        if(noteType.equals(getString(R.string.edit))){
            Log.d("noteType", "$noteType" )
            val noteTitle=intent.getStringExtra(getString(R.string.titleoftask))
           val noteDesc=intent.getStringExtra(getString(R.string.discriptionoftask))
            // val id = intent.getStringExtra("todoID")
            val noteid = intent.getStringExtra(getString(R.string.uniqueIdForTask))
            Log.d("idofnote", "$noteid" )
            Log.d("noteTitle", "$noteTitle" )
            enterButton.text = getString(R.string.update_note)
            edtTitleOfNote.setText(noteTitle)
            edtNoteDiscripton.setText(noteDesc)

            //Delete functionality
            if(noteType.equals("Edit"))
            {
                binding.btnDelete.visibility= View.VISIBLE
                binding.btnDelete.setOnClickListener {
                    noteidp?.let { it1 ->
                        database.child(it1).removeValue().addOnSuccessListener {
                            edtTitleOfNote.text.clear()
                            edtNoteDiscripton.text.clear()


                            Toast.makeText(this, "Successfully Deleted", Toast.LENGTH_SHORT).show()

                        }.addOnFailureListener {
                            Toast.makeText(this, " Failed to delete", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }else
            {
                Toast.makeText(this, "Please create note ", Toast.LENGTH_SHORT).show()
            }





        }
        else
        {
            enterButton.text = getString(R.string.save_note)

        }







        binding.enterButton.setOnClickListener {



            val titleofnote = binding.edtTitleOfNote.text.toString()
            val discription = binding.edtNoteDiscripton.text.toString()
            val idForNote = UUID.randomUUID().toString()










            //val timetext = ServerValue.TIMESTAMP.toString()

            database = FirebaseDatabase.getInstance().getReference(getString(R.string.databaseRefTodo))

            val User = User(titleofnote, discription,idForNote)
            if(!noteType.equals("Edit")) {

                if (!TextUtils.isEmpty(titleofnote) && !TextUtils.isEmpty(discription)) {

                    database.child(idForNote).setValue(User).addOnSuccessListener {
                        binding.edtTitleOfNote.text.clear()
                        binding.edtNoteDiscripton.text.clear()
                        //database.child(idForNote).child("timestamp").setValue(ServerValue.TIMESTAMP)

                        Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()

                    }.addOnFailureListener {

                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()


                    }


                } else
                    Toast.makeText(this, "Fill empty fields", Toast.LENGTH_SHORT).show()
            }
            else
            {
                val note= mapOf(
                    getString(R.string.titleoftask) to titleofnote,
                    getString(R.string.discriptionoftask) to discription,
                    getString(R.string.uniqueIdForTask) to noteidp
                )

                noteidp?.let { it1 ->
                    database.child(it1).updateChildren(note).addOnSuccessListener {
                        edtTitleOfNote.text.clear()
                        edtNoteDiscripton.text.clear()

                        Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(this, "Failed to update", Toast.LENGTH_SHORT).show()
                    }
                }




            }

        }


    }

    //fun deletefun(){



}
