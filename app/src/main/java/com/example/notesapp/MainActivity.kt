package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.MainActivityBinding
import com.example.realtimedatabasekotlin.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.main_activity.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var binding : MainActivityBinding
    private lateinit var database : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.showList.setOnClickListener {
            intent = Intent(applicationContext, TodoListActivity::class.java)
            startActivity(intent)
        }




        binding.enterButton.setOnClickListener {


            val titleofnote = binding.edtTitleOfNote.text.toString()
            val discription = binding.edtNoteDiscripton.text.toString()
            val idForNote = UUID.randomUUID().toString()

            database = FirebaseDatabase.getInstance().getReference("Todo")

            val User = User(titleofnote,discription)
            if(!TextUtils.isEmpty(titleofnote) && !TextUtils.isEmpty(discription))
                 {
                    database.child(idForNote).setValue(User).addOnSuccessListener {

                            binding.edtTitleOfNote.text.clear()
                            binding.edtNoteDiscripton.text.clear()


                            Toast.makeText(this,"Successfully Saved", Toast.LENGTH_SHORT).show()

                        }.addOnFailureListener{

                            Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()


            }       }else
                Toast.makeText(this,"Fill empty fields", Toast.LENGTH_SHORT).show()


        }





    }
}
