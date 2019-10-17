package hr.ferit.matijasokol.notesapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import hr.ferit.matijasokol.notesapp.database.repository.NoteRepositoryImpl
import hr.ferit.matijasokol.notesapp.model.Note

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepository = NoteRepositoryImpl(application)
    private val allNotes = noteRepository.getAllNotes()

    fun insert(note: Note){
        noteRepository.insert(note)
    }

    fun update(note: Note){
        noteRepository.update(note)
    }

    fun delete(note: Note){
        noteRepository.delete(note)
    }

    fun deleteAllNotes(){
        noteRepository.deleteAllNotes()
    }

    fun getAllNotes(): LiveData<List<Note>>{
        return allNotes
    }

}