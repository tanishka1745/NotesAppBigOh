package com.example.notesapp

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.RoomDb.Note
import com.example.notesapp.UI.MainActivity
import com.example.notesapp.ViewModels.NoteViewModel
import com.example.notesapp.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    private lateinit var noteViewModel: NoteViewModel
    private var noteId: Int? = null  // Store note ID if editing

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
        }

        // Save Button Click Listener
        binding.save
            .setOnClickListener {
            showSaveDialog()
        }

        binding.back.setOnClickListener {
            val intent= Intent(this, MainActivity::class.java)
        }



    }

    private fun showSaveDialog() {
        val dialog = Dialog(this)
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_save_confirm, null)
        dialog.setContentView(dialogView)

        val tvMessage: TextView = dialogView.findViewById(R.id.tvMessage)
        val btnSave: Button = dialogView.findViewById(R.id.btnSave)
        val btnCancel: Button = dialogView.findViewById(R.id.btnCancel)

        // Set custom message if needed
        tvMessage.text = "Do you want to save this note?"

        // Save button logic
        btnSave.setOnClickListener {
            saveNote()  // Your existing save note function
            dialog.dismiss()
        }

        // Cancel button logic
        btnCancel.setOnClickListener {
            dialog.dismiss()  // Close dialog without saving
        }

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    private fun saveNote() {
        val title = binding.inputNoteTitle.text.toString()
        val content = binding.inputNote.text.toString()

        if (title.isNotEmpty() && content.isNotEmpty()) {
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


}
