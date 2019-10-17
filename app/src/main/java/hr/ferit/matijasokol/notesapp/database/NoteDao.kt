package hr.ferit.matijasokol.notesapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import hr.ferit.matijasokol.notesapp.model.Note

@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM Notes")
    fun deleteAllNotes()

    @Query("SELECT * FROM Notes ORDER BY priority DESC")
    fun getAllNotes(): LiveData<List<Note>>
}