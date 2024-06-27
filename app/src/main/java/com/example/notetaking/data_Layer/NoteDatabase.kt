package com.example.notetaking.data_Layer

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(exportSchema = true, entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract val notesDao: NotesDao
}