package com.example.notetaking.ui_Layer

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notetaking.data_Layer.Note
import com.example.notetaking.data_Layer.NoteDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class NoteViewModel @Inject constructor(noteDatabase: NoteDatabase) : ViewModel() {
    private val dao = noteDatabase.notesDao

    private val isSortedByDate = MutableStateFlow(true)
    private var notes = isSortedByDate.flatMapLatest {
        if (it) {
            dao.getAllOrderedByDate()
        } else {
            dao.getAllOrderedByTitle()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val _state = MutableStateFlow(NoteState())
    val state = combine(_state,isSortedByDate,notes){
        state,isSortedByDate,notes ->
        state.copy(notes = notes)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteState())


    fun OnEvent(event: NoteEvent) {

        when (event) {
            is NoteEvent.DeleteNote ->{
                viewModelScope.launch {
                    dao.delete(event.note)

            }
            }
            is NoteEvent.SaveNote -> {
                val note = Note(
                    title = state.value.title.value,
                    description = state.value.description.value,
                    date = System.currentTimeMillis()
                )
                viewModelScope.launch {
                    dao.upsert(note)
                    _state.update {
                        it.copy(title = mutableStateOf(""), description = mutableStateOf(""))
                    }
            }
        }
            NoteEvent.SortNotes -> {
                isSortedByDate.value = !isSortedByDate.value
            }
        }
    }
}