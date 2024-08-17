package com.example.notes.data.repository

import com.example.notes.data.local.NoteDao
import com.example.notes.data.model.Note
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao) {

    suspend fun getAllNotes(): List<Note> {
        return noteDao.getAll()
    }

    suspend fun getFavourites(): List<Note>{
        return noteDao.getFavourites()
    }

    suspend fun insert(note: Note){
        return noteDao.insert(note)
    }

    suspend fun update(note: Note){
        return noteDao.update(note)
    }

    suspend fun delete(note: Note){
        return noteDao.delete(note)
    }

    suspend fun getById(id: Int): Note?{
        return noteDao.getById(id)
    }

    suspend fun deleteById(id: Int){
        return noteDao.deleteById(id)
    }

}