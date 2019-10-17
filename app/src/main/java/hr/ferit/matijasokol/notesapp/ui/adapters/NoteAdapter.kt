package hr.ferit.matijasokol.notesapp.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import hr.ferit.matijasokol.notesapp.R
import hr.ferit.matijasokol.notesapp.model.Note
import kotlinx.android.synthetic.main.note_item.view.*

class NoteAdapter(private val interaction: Interaction? = null) :
    ListAdapter<Note, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view, interaction)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NoteViewHolder -> {
                holder.bind(getItem(position))
            }
        }
    }

    fun getNoteAt(position: Int): Note = getItem(position)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Note>() {

            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }
        }
    }

    class NoteViewHolder(
        private val containerView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(containerView) {

        fun bind(item: Note) {
            with(containerView) {
                textViewTitle.text = item.title
                textViewPriority.text = item.priority.toString()
                textViewDescription.text = item.description

                if (adapterPosition != RecyclerView.NO_POSITION){
                    setOnClickListener { interaction?.onItemClicked(adapterPosition, item) }
                }
            }
        }
    }

    interface Interaction {
        fun onItemClicked(position: Int, item: Note)
    }
}