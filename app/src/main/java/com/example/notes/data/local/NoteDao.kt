package com.example.notes.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notes.data.model.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    suspend fun getAll(): List<Note>

    @Query("SELECT * FROM note WHERE isFavourite = 1")
    suspend fun getFavourites(): List<Note>

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getById(id: Int): Note?

    @Query("DELETE FROM note WHERE id = :id")
    suspend fun deleteById(id: Int)
}