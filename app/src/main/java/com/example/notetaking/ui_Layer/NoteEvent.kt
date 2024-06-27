package com.example.notetaking.ui_Layer

import com.example.notetaking.data_Layer.Note

sealed interface NoteEvent {
    object SortNotes : NoteEvent
    data class DeleteNote(val note: Note) : NoteEvent
    data class SaveNote(val title :String , val description : String) : NoteEvent
}