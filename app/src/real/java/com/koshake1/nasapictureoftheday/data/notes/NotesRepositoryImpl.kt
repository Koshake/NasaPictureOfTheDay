package com.koshake1.nasapictureoftheday.data.notes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

private val idRandom = Random(0)
val noteId: Long
    get() = idRandom.nextLong()

class NotesRepositoryImpl : NotesRepository {

    private val TAG = "1111"
    private val notes: MutableList<NotesData> = mutableListOf()

    private val allNotes = MutableLiveData(getListForNotify())

    override fun observeNotes(): LiveData<List<NotesData>> {
        return allNotes
    }

    override fun addOrReplaceNote(newNote: NotesData) {
        notes.find { it.id == newNote.id }?.let {
            if (it == newNote) return
            notes.remove(it)
        }
        notes.add(newNote)

        Log.d(TAG, "add ${newNote.title} ")
        notifyChanges()
    }

    override fun addNote(newNote: NotesData, position: Int) {
        notes.add(position, newNote)
        Log.d(TAG, "add ${newNote.title} $position ")
        notifyChanges()
    }

    override fun deleteNote(notesData: NotesData) {
        notes.find { it.id == notesData.id }?.let {
            Log.d(TAG, "remove ${it.title} ")
            notes.remove(it)
            notifyChanges()
        }
    }

    override fun deleteNoteAt(position: Int) {
        Log.d(TAG, "remove ${notes[position].title} $position ")
        notes.removeAt(position)
        notifyChanges()
    }

    override fun replaceNote(fromPosition: Int, toPosition: Int) {
        notes.removeAt(fromPosition).apply {
            notes.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyChanges()
    }

    override fun replaceNoteByItem(notesDataFrom: NotesData, notesDataTo: NotesData) {

        val fromPosition = notes.indexOf(notesDataFrom)
        val toPosition = notes.indexOf(notesDataTo)
        notes.removeAt(fromPosition).apply {
            notes.add(toPosition, this)
        }
        notifyChanges()
    }

    fun getListForNotify(): List<NotesData> = notes.toMutableList()

    private fun notifyChanges() {
        allNotes.postValue(
            notes
        )
    }
}
