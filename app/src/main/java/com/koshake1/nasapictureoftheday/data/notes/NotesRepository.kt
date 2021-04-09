package com.koshake1.nasapictureoftheday.data.notes

import androidx.lifecycle.LiveData

interface NotesRepository {
    fun observeNotes(): LiveData<List<NotesData>>
    fun addOrReplaceNote(newNote: NotesData)
    fun addNote(newNote: NotesData, position : Int)
    fun deleteNote(notesData: NotesData)
    fun deleteNoteAt(position: Int)
    fun replaceNote(fromPosition: Int, toPosition: Int)
    fun replaceNoteByItem(notesDataFrom: NotesData, notesDataTo: NotesData)
}
