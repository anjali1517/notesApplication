package com.example.notesapplication.Adapter

import DbHandler.notesDbHandler
import Model.notes
import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapplication.EditActivity
import com.example.notesapplication.R

//viewholder is just a nested class of recyclerview

// create a variable in arraylist in notes structure as a parameter of NotesAdapter
class NotesAdapter(var note:ArrayList<notes>,var context:Context) : RecyclerView.Adapter<NotesAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // to find the textview from layout file
        //why written here view ->because we pass the whole layout in ViewHolder
        var txtTitle:TextView = view.findViewById(R.id.txtTitle)
        var txtDetail:TextView = view.findViewById(R.id.txtDescription)
        var imgDelete:ImageView = view.findViewById(R.id.imgDelete)
        var rootGroup:RelativeLayout = view.findViewById(R.id.rootGroup)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //parent = it is the context of parent class that is implement from parent
        //in inflate = 1.resource
        //              2.layout
        //              3.layout file
        //               4.root-that is viewGroup of createViewHolder
        //              5.attchtoview-to attach the the whole view then give value is false either give true
        var view = LayoutInflater.from(parent.context).inflate(R.layout.notes_item_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //in holder there is hold ViewHolder variable and this is used to set the text in layout
        //here,note is value of information(data) and it is in array form so there is a position to check the wich index is there
        //and which we want replace the data and to avoid the error convert in toString()
        holder.txtTitle.setText(note[position].notes_title.toString())
        holder.txtDetail.setText(note[position].notes_details.toString())

        holder.imgDelete.setOnClickListener(){
            var dbHandler = notesDbHandler(context)

            var rows = dbHandler.deleteNotes(note[position].notes_id!!)

            if(rows>0){
                Toast.makeText(context,"Note deleted",Toast.LENGTH_LONG).show()
                //to remove that particular position row
                note.removeAt(position)
                notifyItemRemoved(position)
                //to get changble row
                notifyItemRangeChanged(position,note.size)
            }
        }

        holder.rootGroup.setOnClickListener(){
            var intent:Intent = Intent(context,EditActivity::class.java)
            intent.putExtra("_id",note[position].notes_id)
            //here context is a reference of activity
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return note.size
    }

}