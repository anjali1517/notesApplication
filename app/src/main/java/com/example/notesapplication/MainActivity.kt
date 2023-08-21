package com.example.notesapplication


import DbHandler.notesDbHandler
import Model.notes
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapplication.Adapter.NotesAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var btn:FloatingActionButton
    lateinit var recycler:RecyclerView
    lateinit var toolbar:Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById(R.id.btnAdd)
        toolbar = findViewById(R.id.toolbar)
        recycler = findViewById(R.id.recycler)

        setSupportActionBar(toolbar)


    }

    override fun onStart() {
        super.onStart()

        //we have to write recycler in onStart because mainActivity is onPause that's why it is refresh from onStrart
        var notesList = ArrayList<notes>()

        var dbHandler = notesDbHandler(this)
        notesList = dbHandler.readNotes()
        //to attach the adapter with recyclerView
        recycler.adapter = NotesAdapter(notesList,this)

        btn.setOnClickListener(){
            var intent:Intent = Intent(this,NotesActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //first parameter is path and second is object of menu
        menuInflater.inflate(R.menu.action_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuProfile->{
                Toast.makeText(this,"you click on profile", Toast.LENGTH_LONG).show()
            }
            R.id.menuSetting->{
                Toast.makeText(this,"you click on setting", Toast.LENGTH_LONG).show()
            }
            R.id.menuSearch->{
                Toast.makeText(this,"you click on search", Toast.LENGTH_LONG).show()
            }
            R.id.menuShare->{
                Toast.makeText(this,"you click on share", Toast.LENGTH_LONG).show()
            }

        }
        return super.onOptionsItemSelected(item)
    }


}