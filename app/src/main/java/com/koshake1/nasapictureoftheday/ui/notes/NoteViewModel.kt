package com.koshake1.nasapictureoftheday.ui.notes

import androidx.lifecycle.ViewModel
import com.koshake1.nasapictureoftheday.data.notes.NotesData
import com.koshake1.nasapictureoftheday.data.notes.NotesRepositoryImpl
import com.koshake1.nasapictureoftheday.utils.DELETE_ERROR
import com.koshake1.nasapictureoftheday.utils.DELETE_SUCCESS
import com.koshake1.nasapictureoftheday.utils.SAVING_ERROR
import com.koshake1.nasapictureoftheday.utils.SAVING_SUCCESS


class NoteViewModel(var note: NotesData?) : ViewModel() {

    private var showErrorMessage : String? = null

    fun updateNote(text: String) {
        note = (note ?: generateNote()).copy(note = text)
    }

    fun updateTitle(text: String) {
        note = (note ?: generateNote()).copy(title = text)
    }

    fun saveNote() {
        note?.let {
            try {
                NotesRepositoryImpl.addOrReplaceNote(it)
            } catch (th: Throwable) {
                showErrorMessage = SAVING_ERROR
            }
            showErrorMessage = SAVING_SUCCESS
        }
    }

    fun deleteNote() {
        note?.let {
            try {
                NotesRepositoryImpl.deleteNote(it)
            } catch (th: Throwable) {
                showErrorMessage = DELETE_ERROR
            }
            showErrorMessage = DELETE_SUCCESS
        }
    }

    fun showError() : String? = showErrorMessage

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