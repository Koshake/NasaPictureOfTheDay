package com.koshake1.nasapictureoftheday.ui.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.koshake1.nasapictureoftheday.data.notes.NotesRepository
import com.koshake1.nasapictureoftheday.data.notes.NotesRepositoryImpl

class NotesViewModel(private val notesRepository: NotesRepository) : ViewModel() {

    fun observeViewState(): LiveData<ViewState> = notesRepository.observeNotes()
        .map {
            if (it.isEmpty()) ViewState.EMPTY else ViewState.Value(it)
        }
}