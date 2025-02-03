package com.example.notesapp.Adapters


import android.app.Dialog
import android.content.Context
import com.example.notesapp.RoomDb.Note
import com.example.notesapp.databinding.ItemNoteBinding


import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.UI.EditActivity
import com.example.notesapp.ViewModels.NoteViewModel

import kotlin.random.Random


class NoteAdapter(private val onDeleteNote: (Note) -> Unit) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
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
            binding.rootLayout.setBackgroundColor(getRandomColor())


            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, EditActivity::class.java).apply {
                    putExtra("NOTE_ID", note.id)
                    putExtra("NOTE_TITLE", note.title)
                    putExtra("NOTE_CONTENT", note.content)
                }
                binding.root.context.startActivity(intent)
            }

            // Long Press Listener to show Delete Confirmation Dialog
            binding.root.setOnLongClickListener {
                showDeleteDialog(binding.root.context, note)
                true
            }
        }

        private fun showDeleteDialog(context: Context, note: Note) {
            val dialog = Dialog(context)
            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_delete_confirm, null)
            dialog.setContentView(dialogView)

            val btnDelete: Button = dialogView.findViewById(R.id.btnDelete)
            val btnCancel: Button = dialogView.findViewById(R.id.btnCancel)

            // Delete Button Action
            btnDelete.setOnClickListener {
                onDeleteNote(note)  // Call the delete function
                dialog.dismiss()
            }

            // Cancel Button Action
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

            // Transparent background for rounded corners
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()
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
