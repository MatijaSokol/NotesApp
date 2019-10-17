package hr.ferit.matijasokol.notesapp.database.repository

import androidx.lifecycle.LiveData
import hr.ferit.matijasokol.notesapp.model.Note

interface NoteRepository {

    fun insert(note: Note)

    fun update(note: Note)

    fun delete(note: Note)

    fun deleteAllNotes()

    fun getAllNotes(): LiveData<List<Note>>
}