package com.example.notesapp.Adapters


import com.example.notesapp.RoomDb.Note
import com.example.notesapp.databinding.ItemNoteBinding


import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.EditActivity

import kotlin.random.Random


class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private var notes: List<Note> = listOf()

    private fun getRandomColor(): Int {
        val random = Random.Default
        return Color.rgb(
            random.nextInt(100, 256),  // R
            random.nextInt(100, 256),  // G
            random.nextInt(100, 256)   // B
        )
    }

    inner class NoteViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.title.text = note.title
            binding.content.text = note.content

            binding.root.setBackgroundColor(getRandomColor())
            // Click listener to open Add/Edit Activity with note details
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, EditActivity::class.java).apply {
                    putExtra("NOTE_ID", note.id)
                    putExtra("NOTE_TITLE", note.title)
                    putExtra("NOTE_CONTENT", note.content)
                }
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount() = notes.size

    fun submitList(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}

