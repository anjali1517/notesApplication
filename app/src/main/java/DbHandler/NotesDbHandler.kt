package DbHandler

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import Model.notes


class notesDbHandler(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {

        const val DB_NAME = "notes_db"

        const val DB_VERSION = 1

        //table name
        const val TABLIE_NAME = "note_tbl"

        //table colums name
        const val NOTES_ID = "notes_id"
        const val NOTES_NAME = "notes_name"
        const val NOTES_DETAILS = "notes_detail"

    }

    override fun onCreate(p0: SQLiteDatabase?) {

        val CREATE_TABLE_NOTES: String = "CREATE TABLE $TABLIE_NAME(" +
                "$NOTES_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$NOTES_NAME TEXT NOT NULL," +
                "$NOTES_DETAILS TEXT NOT NULL)"

        p0?.execSQL(CREATE_TABLE_NOTES)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

        p0!!.execSQL("DROP TABLE if exists $TABLIE_NAME")
        onCreate(p0)
    }

    fun insertNote(notes: notes): Long {

        var db = this.writableDatabase

        var cv = ContentValues()
        cv.put(NOTES_NAME, notes.notes_title)
        cv.put(NOTES_DETAILS, notes.notes_details)

        var row: Long = db.insert(TABLIE_NAME, null, cv)

        return row

    }

    fun readNotes(): ArrayList<notes> {
        var db = this.readableDatabase

        var noteCursor = db.rawQuery("SELECT * FROM $TABLIE_NAME", null)
        //rawQuery return in cursor type
        //we want the data in particular structure
        var noteList: ArrayList<notes> = ArrayList()
        if (noteCursor.moveToFirst()) {
            do {
                noteList.add(
                    notes(
                        noteCursor.getInt(0),
                        noteCursor.getString(1),
                        noteCursor.getString(2)
                    )
                )
            } while (noteCursor.moveToNext())

        }
        return noteList
    }

    fun deleteNotes(id: Int): Int {
        var db = this.writableDatabase

        var row: Int = db.delete("$TABLIE_NAME", "$NOTES_ID=?", arrayOf<String>(id.toString()))
        return row
    }

    fun readSpecificNotes(id: Int): notes {

        var db = this.readableDatabase

        var notesCursor = db.rawQuery("SELECT * FROM $TABLIE_NAME WHERE $NOTES_ID=$id", null)

        notesCursor.moveToFirst()

        var notes: notes =
            notes(notesCursor.getInt(0), notesCursor.getString(1), notesCursor.getString(2))

        return notes
    }

    fun updateNotes(notes: notes): Int {
        var db = this.writableDatabase

        var cv = ContentValues()
        cv.put(NOTES_NAME, notes.notes_title)
        cv.put(NOTES_DETAILS, notes.notes_details)
        //here question mark means what value we want
        var row:Int =
            db.update("$TABLIE_NAME", cv, "$NOTES_ID=?", arrayOf<String>(notes.notes_id.toString()))

        return row
    }



}
