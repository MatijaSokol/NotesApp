package hr.ferit.matijasokol.notesapp.ui.activities

import android.app.Activity
import android.content.Intent
import android.provider.ContactsContract
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import hr.ferit.matijasokol.notesapp.ui.activities.base.BaseActivity
import hr.ferit.matijasokol.notesapp.R
import hr.ferit.matijasokol.notesapp.common.displayToast
import hr.ferit.matijasokol.notesapp.model.Note
import kotlinx.android.synthetic.main.activity_add_note.*
import kotlin.jvm.internal.MagicApiIntrinsics

class AddEditNoteActivity : BaseActivity() {

    override fun getLayoutResourceId(): Int = R.layout.activity_add_note

    override fun setUpUi() {
        setNumberPicker()
        setActivityActionBar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.saveNoteItem -> {
                checkInput()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkInput(){
        val title = editTextTitle.text.toString().trim()
        val description = editTextDescription.text.toString().trim()
        val priority = numberPickerPriority.value

        if (title.isEmpty() || description.isEmpty()){
            displayToast(getString(R.string.insert_data))
            return
        }

        val id: Int

        if (intent.hasExtra(MainActivity.EXTRA_NOTE_EDIT)){
            val note: Note = intent.getParcelableExtra(MainActivity.EXTRA_NOTE_EDIT)
                ?: Note(getString(R.string.unknown), getString(R.string.unknown), 1)
            id = note.id
            val newNote = Note(title, description, priority, id)
            returnToHomeActivity(newNote, MainActivity.EXTRA_NOTE_EDIT)
        } else {
            val note = Note(title, description, priority)
            returnToHomeActivity(note, EXTRA_NOTE_ADD)
        }
    }

    private fun returnToHomeActivity(note: Note, extraMessage: String){
        val intent = Intent()
        intent.putExtra(extraMessage, note)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun setActivityActionBar(){
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        title = when(intent.hasExtra(MainActivity.EXTRA_NOTE_EDIT)){
            true -> {
                setViews()
                getString(R.string.edit_note)
            }
            else -> getString(R.string.save_note)
        }
    }

    private fun setViews(){
        val note: Note = intent.getParcelableExtra(MainActivity.EXTRA_NOTE_EDIT)
            ?: Note(getString(R.string.unknown), getString(R.string.unknown), 1)

        editTextTitle.setText(note.title, TextView.BufferType.EDITABLE)
        editTextDescription.setText(note.description, TextView.BufferType.EDITABLE)
        numberPickerPriority.value = note.priority
    }

    private fun setNumberPicker(){
        numberPickerPriority.apply {
            minValue = 1
            maxValue = 10
        }
    }

    companion object {
        const val EXTRA_NOTE_ADD = "hr.ferit.matijasokol.notesapp.ui.activities.EXTRA_NOTE_ADD"
    }
}
