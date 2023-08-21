package com.example.notesapplication

import DbHandler.notesDbHandler
import Model.notes
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View.inflate
import android.widget.*
import androidx.appcompat.widget.Toolbar

class NotesActivity : AppCompatActivity() {

    lateinit var edtTitle:EditText
    lateinit var edtNote:EditText
    lateinit var btnAdd:ImageButton
    lateinit var toolbar:Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        edtTitle = findViewById(R.id.edtTitle)
        edtNote = findViewById(R.id.edtBody)
        btnAdd = findViewById(R.id.imgBtn)
        toolbar = findViewById(R.id.toolbar)

       // setSupportActionBar(toolbar) if actionbar is going to be noactionbar

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStart() {
        super.onStart()
        btnAdd.setOnClickListener(){
            AddNotestoDB()
        }
    }

    private fun AddNotestoDB() {
       var title:String? = edtTitle.text.toString()
        var details:String? = edtNote.text.toString()

        if(title.isNullOrEmpty() && details.isNullOrEmpty()){
            //error
            edtTitle.error = "title is required"
            edtNote.error = "note is required"
        }
        else{
            //insert the data from notesDbHandler

            var db = notesDbHandler(this)

           var row = db.insertNote(notes(null,title,details))

            if(row<=-1){
                Toast.makeText(this,"Notes cannot be added",Toast.LENGTH_LONG).show()
                Log.d("AddActivity!!!","Notes cannot be added and row=$row")
            }
            else{
                Toast.makeText(this,"Notes add successfully",Toast.LENGTH_LONG).show()
                Log.d("AddActivity!!!","Notes add succesfully and row=$row")
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}

