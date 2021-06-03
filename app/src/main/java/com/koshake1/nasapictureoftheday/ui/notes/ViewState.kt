package com.koshake1.nasapictureoftheday.ui.notes

import com.koshake1.nasapictureoftheday.data.notes.NotesData

sealed class ViewState {
    data class Value(val notes: List<NotesData>) : ViewState()
    object EMPTY : ViewState()
}