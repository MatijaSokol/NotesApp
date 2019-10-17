package hr.ferit.matijasokol.notesapp.ui.adapters

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.matijasokol.notesapp.model.Note

class DragSwipeCallback(
    private val noteAdapter: NoteAdapter,
    private val onDragSwipeListener: OnDragSwipeListener
) : ItemTouchHelper.SimpleCallback(
    0,
    ItemTouchHelper.LEFT
) {

    interface OnDragSwipeListener {
        fun onItemSwiped(note: Note)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onDragSwipeListener.onItemSwiped(noteAdapter.getNoteAt(viewHolder.adapterPosition))
    }
}