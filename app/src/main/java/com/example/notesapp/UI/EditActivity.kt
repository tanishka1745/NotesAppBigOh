package com.example.notesapp.UI

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.R
import com.example.notesapp.RoomDb.Note
import com.example.notesapp.ViewModels.NoteViewModel
import com.example.notesapp.databinding.ActivityEditBinding


class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    private lateinit var noteViewModel: NoteViewModel
    private var noteId: Int? = null  // Store note ID if editing
    private var originalTitle: String? = null
    private var originalContent: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        // Retrieve data if editing an existing note
        noteId = intent.getIntExtra("NOTE_ID", -1)
        val noteTitle = intent.getStringExtra("NOTE_TITLE")
        val noteContent = intent.getStringExtra("NOTE_CONTENT")

        if (noteId != -1) {
            binding.inputNoteTitle.setText(noteTitle)
            binding.inputNote.setText(noteContent)
            originalTitle = noteTitle
            originalContent = noteContent
            binding.preview.visibility = View.VISIBLE
        }

        // Save Button Click Listener
        binding.save.setOnClickListener {
            saveNote()  // Call saveNote directly
        }

        binding.back.setOnClickListener {
            finish()  // This will navigate back to the previous activity in the stack
        }

//        binding.delete.setOnClickListener {
//            showDeleteDialog()
//        }
    }

    private fun showDeleteDialog() {
        val dialog = Dialog(this)
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_delete_confirm, null)
        dialog.setContentView(dialogView)

        val tvMessage: TextView = dialogView.findViewById(R.id.tvMessage)
        val btnDelete: Button = dialogView.findViewById(R.id.btnDelete)
        val btnCancel: Button = dialogView.findViewById(R.id.btnCancel)

        // Set delete confirmation message
        tvMessage.text = "Are you sure you want to delete this note?"

        // Delete button logic
        btnDelete.setOnClickListener {
            deleteNote()  // Your delete function
            dialog.dismiss()
        }

        // Cancel button logic
        btnCancel.setOnClickListener {
            dialog.dismiss()  // Close dialog
        }

        // Transparent background
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    private fun deleteNote() {
        if (noteId != -1) {
            noteId?.let { noteViewModel.deleteNoteById(it) }
            Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show()
            finish() // Close activity and return to main screen
        }
    }

    private fun saveNote() {
        var title = binding.inputNoteTitle.text.toString().trim()
        var content = binding.inputNote.text.toString().trim()

        // Remove extra white spaces from display
        title = title.replace("\\s+".toRegex(), " ")
        content = content.replace("\\s+".toRegex(), " ")

        // Check if there's any update
        if (title == originalTitle && content == originalContent) {
            Toast.makeText(this, "No changes made to the note", Toast.LENGTH_SHORT).show()
        } else if (title.isNotEmpty() && content.isNotEmpty()) {
            if (noteId != -1) {
                // Update existing note
                val updatedNote = Note(id = noteId!!, title = title, content = content)
                noteViewModel.update(updatedNote)
            } else {
                // Insert new note
                val newNote = Note(title = title, content = content)
                noteViewModel.insert(newNote)
            }
            finish()
        } else {
            Toast.makeText(this, "Title and Content cannot be empty", Toast.LENGTH_SHORT).show()
        }
    }
    // when you touch anywhere on the screen keyboard would be disappear
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}