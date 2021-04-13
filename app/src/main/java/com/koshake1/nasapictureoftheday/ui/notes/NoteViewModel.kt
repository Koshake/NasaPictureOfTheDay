package com.koshake1.nasapictureoftheday.ui.notes

import androidx.lifecycle.ViewModel
import com.koshake1.nasapictureoftheday.data.notes.NotesData
import com.koshake1.nasapictureoftheday.data.notes.NotesRepositoryImpl

class NoteViewModel(var note: NotesData?) : ViewModel() {

    fun updateNote(text: String) {
        note = (note ?: generateNote()).copy(note = text)
    }

    fun updateTitle(text: String) {
        note = (note ?: generateNote()).copy(title = text)
    }

    fun saveNote() {
        note?.let {
            NotesRepositoryImpl.addOrReplaceNote(it)
        }
    }

    override fun onCleared() {
        super.onCleared()

        note?.let {
            NotesRepositoryImpl.addOrReplaceNote(it)
        }
    }

    private fun generateNote(): NotesData {
        return NotesData()
    }
}