package com.example.noteapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(
    private val notes: List<Note>
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var onItemClickListener: ((Note) -> Unit)? = null

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        private val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        private val textViewPriority: TextView = itemView.findViewById(R.id.textViewPriority)
        private val cardView: CardView = itemView.findViewById(R.id.cardView)

        fun bind(note: Note) {
            textViewTitle.text = note.title
            textViewDescription.text = note.description
            textViewPriority.text = "Priority: ${note.priority}"

            // Priority color coding
            when (note.priority) {
                1 -> cardView.setCardBackgroundColor(itemView.context.getColor(R.color.priority_low))
                2 -> cardView.setCardBackgroundColor(itemView.context.getColor(R.color.priority_medium))
                else -> cardView.setCardBackgroundColor(itemView.context.getColor(R.color.priority_high))
            }

            itemView.setOnClickListener {
                onItemClickListener?.invoke(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note_list, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount(): Int = notes.size

    fun setOnItemClickListener(listener: (Note) -> Unit) {
        onItemClickListener = listener
    }
}