package com.example.notesapplication

import DbHandler.notesDbHandler
import Model.notes
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton

class EditActivity : AppCompatActivity() {

    var _id:Int = 0
    lateinit var edtEditTilte:EditText
    lateinit var edtEditDetails:EditText
    lateinit var btnUpadate:AppCompatButton
    lateinit var notes:notes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        edtEditTilte = findViewById(R.id.edtEditTitle)
        edtEditDetails = findViewById(R.id.edtEditDetails)
        btnUpadate = findViewById(R.id.update)


        _id = intent.getIntExtra("_id", 0)

        Log.d("EditActivity", "this is id:$_id")

        var dbHandler = notesDbHandler(this)

         notes =  dbHandler.readSpecificNotes(_id)

        edtEditTilte.setText(notes.notes_title)
        edtEditDetails.setText(notes.notes_details)

    }

    override fun onStart() {
        super.onStart()


        btnUpadate.setOnClickListener(){

            if(notes.notes_title==edtEditTilte.text.toString() && notes.notes_details==edtEditDetails.text.toString()){

                finish()
            }
            else{

                var dbHandler = notesDbHandler(this)

                var row = dbHandler.updateNotes(notes(_id,edtEditTilte.text.toString(),edtEditDetails.text.toString()))

                if(row>0){
                    Toast.makeText(this,"Notes is Updated",Toast.LENGTH_LONG).show()
                    finish()
                }
                else {
                    Toast.makeText(this,"Notes is not Updated",Toast.LENGTH_LONG).show()
                    finish()

                }

            }

        }

    }
}

