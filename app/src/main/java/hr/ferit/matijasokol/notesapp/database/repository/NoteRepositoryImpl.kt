package hr.ferit.matijasokol.notesapp.database.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import hr.ferit.matijasokol.notesapp.database.NoteDao
import hr.ferit.matijasokol.notesapp.database.NoteDatabase
import hr.ferit.matijasokol.notesapp.model.Note

class NoteRepositoryImpl(application: Application) : NoteRepository {

    private val noteDatabase = NoteDatabase.getInstance(application)
    private val noteDao = noteDatabase.noteDao()

    override fun insert(note: Note) {
        NoteEditorAsyncTask<Note>(noteDao, AsyncTaskCommands.InsertNoteCommand).execute(note)
    }

    override fun update(note: Note) {
        NoteEditorAsyncTask<Note>(noteDao, AsyncTaskCommands.UpdateNoteCommand).execute(note)
    }

    override fun delete(note: Note) {
        NoteEditorAsyncTask<Note>(noteDao, AsyncTaskCommands.DeleteNoteCommand).execute(note)
    }

    override fun deleteAllNotes() {
        NoteEditorAsyncTask<Unit>(noteDao, AsyncTaskCommands.DeleteAllNotesCommand).execute()
    }

    //automatically on background thread
    override fun getAllNotes(): LiveData<List<Note>> {
        return noteDao.getAllNotes()
    }

    companion object {

        private class NoteEditorAsyncTask<T>(private val noteDao: NoteDao, private val command: AsyncTaskCommands) : AsyncTask<T, Unit, Unit>() {

            override fun doInBackground(vararg p0: T) {
                when(command) {
                    is AsyncTaskCommands.InsertNoteCommand -> noteDao.insert(p0.first()!! as Note)
                    is AsyncTaskCommands.UpdateNoteCommand -> noteDao.update(p0.first()!! as Note)
                    is AsyncTaskCommands.DeleteNoteCommand -> noteDao.delete(p0.first()!! as Note)
                    is AsyncTaskCommands.DeleteAllNotesCommand -> noteDao.deleteAllNotes()
                }
            }
        }
    }
}