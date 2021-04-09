package com.koshake1.nasapictureoftheday.ui.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.koshake1.nasapictureoftheday.data.notes.NotesRepositoryImpl

class NotesViewModel : ViewModel() {
    fun observeViewState(): LiveData<ViewState> = NotesRepositoryImpl.observeNotes()
        .map {
            if (it.isEmpty()) ViewState.EMPTY else ViewState.Value(it)
        }
}