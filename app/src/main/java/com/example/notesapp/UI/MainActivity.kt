package com.example.notesapp.UI


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.Adapters.NoteAdapter
import com.example.notesapp.RoomDb.Note


import com.example.notesapp.ViewModels.NoteViewModel
import com.example.notesapp.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val noteViewModel: NoteViewModel by viewModels()
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeNotes()

        binding.addNotes.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        adapter = NoteAdapter { note -> deleteNote(note) } // Pass delete function
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = adapter
    }

    private fun observeNotes() {
        noteViewModel.allNotes.observe(this) { notes ->
            adapter.submitList(notes)
        }
    }

    private fun deleteNote(note: Note) {
        noteViewModel.deleteNoteById(note.id)
        Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        observeNotes()
    }
}
