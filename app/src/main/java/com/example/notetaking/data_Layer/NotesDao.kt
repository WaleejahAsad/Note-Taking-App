package com.example.notetaking.data_Layer

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Upsert
   suspend fun upsert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM note order by date ASC")
    fun getAllOrderedByDate(): Flow<List<Note>>

    @Query("SELECT * FROM note order by title ASC")
    fun getAllOrderedByTitle(): Flow<List<Note>>
}