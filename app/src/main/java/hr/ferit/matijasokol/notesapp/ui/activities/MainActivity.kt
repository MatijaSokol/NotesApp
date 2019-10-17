package hr.ferit.matijasokol.notesapp.ui.activities

import android.app.Activity
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import hr.ferit.matijasokol.notesapp.R
import hr.ferit.matijasokol.notesapp.common.displayToast
import hr.ferit.matijasokol.notesapp.model.Note
import hr.ferit.matijasokol.notesapp.ui.activities.base.BaseActivity
import hr.ferit.matijasokol.notesapp.ui.adapters.DragSwipeCallback
import hr.ferit.matijasokol.notesapp.ui.adapters.NoteAdapter
import hr.ferit.matijasokol.notesapp.viewModel.NoteViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), NoteAdapter.Interaction, DragSwipeCallback.OnDragSwipeListener {

    private lateinit var noteViewModel: NoteViewModel
    private val noteAdapter = NoteAdapter(this)

    override fun getLayoutResourceId(): Int = R.layout.activity_main

    override fun setUpUi() {
        buttonAdd.setOnClickListener { openAddNoteActivity() }
        setUpRecycler()
        setViewModel()
    }

    private fun openAddNoteActivity() {
        val intent = Intent(this, AddEditNoteActivity::class.java)
        startActivityForResult(intent, ADD_NOTE_REQUEST)
    }

    private fun setViewModel() {
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        noteViewModel.getAllNotes().observe(this, Observer<List<Note>> { notes ->
            noteAdapter.submitList(notes)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            ADD_NOTE_REQUEST -> {
                if (resultCode == Activity.RESULT_OK) {
                    saveNote(data)
                } else {
                    displayToast(getString(R.string.note_not_saved))
                }
            }
            EDIT_NOTE_REQUEST -> {
                if (resultCode == Activity.RESULT_OK) {
                    editNote(data)
                } else {
                    displayToast(getString(R.string.note_not_edited))
                }
            }
            else -> {
                displayToast(getString(R.string.error))
            }
        }
    }

    private fun editNote(data: Intent?) {
        val note: Note = data?.getParcelableExtra(EXTRA_NOTE_EDIT)
            ?: Note(getString(R.string.unknown), getString(R.string.unknown), 1)

        noteViewModel.update(note)
        displayToast(getString(R.string.note_updated))
    }

    private fun saveNote(data: Intent?) {
        val note: Note = data?.getParcelableExtra(AddEditNoteActivity.EXTRA_NOTE_ADD)
            ?: Note(getString(R.string.unknown), getString(R.string.unknown), 1)

        noteViewModel.insert(note)

        displayToast(getString(R.string.note_saved))
    }

    private fun setUpRecycler() {
        recyclerView.apply {
            adapter = noteAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }

        val callback = DragSwipeCallback(noteAdapter, this@MainActivity)
        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(recyclerView)
    }

    override fun onItemClicked(position: Int, item: Note) {
        val intent = Intent(this, AddEditNoteActivity::class.java)
        intent.putExtra(EXTRA_NOTE_EDIT, item)
        startActivityForResult(intent, EDIT_NOTE_REQUEST)
    }

    override fun onItemSwiped(note: Note) {
        noteViewModel.delete(note)
        displayToast(getString(R.string.note_deleted))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.deleteAllNotes -> {
                noteViewModel.deleteAllNotes()
                displayToast(getString(R.string.all_notes_deleted))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val ADD_NOTE_REQUEST = 1
        const val EDIT_NOTE_REQUEST = 2
        const val EXTRA_NOTE_EDIT = "hr.ferit.matijasokol.notesapp.ui.activities.EXTRA_NOTE_EDIT"
    }
}
