package com.example.notesapp.Repository



import androidx.lifecycle.LiveData
import com.example.notesapp.RoomDb.Note
import com.example.notesapp.RoomDb.NoteDao


class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun update(note: Note) {
        noteDao.update(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }
}