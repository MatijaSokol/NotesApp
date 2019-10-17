package hr.ferit.matijasokol.notesapp.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import hr.ferit.matijasokol.notesapp.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        private const val NAME = "Note Database"
        private var INSTANCE: NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    NAME
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }
            return INSTANCE as NoteDatabase
        }

        //insert some default notes on creating
        private val roomCallback = object : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDatabaseAsyncTask(INSTANCE!!)
                    .execute()
            }
        }

        private class PopulateDatabaseAsyncTask(noteDatabase: NoteDatabase) : AsyncTask<Unit, Unit, Unit>() {

            private var noteDao = noteDatabase.noteDao()

            override fun doInBackground(vararg p0: Unit?) {
                noteDao.insert(Note("Title1", "Description1", 1))
                noteDao.insert(Note("Title2", "Description2", 2))
                noteDao.insert(Note("Title3", "Description3", 3))
            }
        }
    }
}