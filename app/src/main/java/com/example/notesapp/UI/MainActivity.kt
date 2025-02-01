package com.example.notesapp.UI


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.Adapters.NoteAdapter
import com.example.notesapp.EditActivity


import com.example.notesapp.R
import com.example.notesapp.ViewModels.NoteViewModel
import com.example.notesapp.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    private val noteViewModel: NoteViewModel by viewModels()
    private lateinit var adapter: NoteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        adapter = NoteAdapter()
        binding.recyclerview.adapter = adapter


        noteViewModel.allNotes.observe(this) { notes ->
            adapter.submitList(notes)
        }

        binding.addNotes.setOnClickListener{
            val intent= Intent(this, EditActivity::class.java)
            startActivity(intent)
        }


    }

}