package com.example.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.data.model.Note
import com.example.notes.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository) :ViewModel() {

    private val _allNotes = MutableStateFlow<List<Note>?>(null)
    val allNotes : StateFlow<List<Note>?> get() = _allNotes


    init {
        getAllNotes()
    }


    private fun getAllNotes() {
        viewModelScope.launch {
            _allNotes.value = repository.getAllNotes()
            println("All notes: ${_allNotes.value}")
        }
    }

    fun insertNote(note: Note) {
        viewModelScope.launch {
            repository.insert(note)
            getAllNotes()
        }
    }
    fun updateNote(note: Note){
        viewModelScope.launch {
            repository.update(note)
            getAllNotes()

        }
    }

    fun deleteNote(note: Note){
        viewModelScope.launch {
            repository.delete(note)
            getAllNotes()
        }
    }

}