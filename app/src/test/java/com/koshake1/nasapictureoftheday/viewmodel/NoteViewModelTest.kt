package com.koshake1.nasapictureoftheday.viewmodel

import com.koshake1.nasapictureoftheday.data.notes.NotesData
import com.koshake1.nasapictureoftheday.ui.notes.NoteViewModel
import com.koshake1.nasapictureoftheday.utils.DELETE_SUCCESS
import com.koshake1.nasapictureoftheday.utils.SAVING_SUCCESS
import org.junit.*

class NoteViewModelTest {

    private lateinit var viewModel: NoteViewModel

    @Test
    fun `ViewModel Note title changed`() {
        val currentNote = NotesData(title = "Title!")
        viewModel = NoteViewModel(currentNote)
        viewModel.updateTitle("New")

        Assert.assertEquals("New", viewModel.note?.title)
    }

    @Test
    fun `ViewModel Note not null after update`() {
        val currentNote = NotesData(title = "Title!")
        viewModel = NoteViewModel(currentNote)
        viewModel.updateTitle("New")

        Assert.assertNotNull(viewModel.note)
    }

    @Test
    fun `ViewModel Note text changed`() {
        val currentNote = NotesData(note = "Text")
        viewModel = NoteViewModel(currentNote)
        viewModel.updateNote("New text")

        Assert.assertNotEquals("Text", viewModel.note?.note)
    }

    @Test
    fun `View model save notes shows successful message`() {
        val currentNote = NotesData(title = "Hello!")
        viewModel = NoteViewModel(currentNote)
        viewModel.saveNote()

        Assert.assertEquals(viewModel.showError(), SAVING_SUCCESS)
    }

    @Test
    fun `View model delete notes shows successful message`() {
        val currentNote = NotesData(title = "Hello!")
        viewModel = NoteViewModel(currentNote)
        viewModel.saveNote()
        viewModel.deleteNote()

        Assert.assertEquals(viewModel.showError(), DELETE_SUCCESS)
    }
}