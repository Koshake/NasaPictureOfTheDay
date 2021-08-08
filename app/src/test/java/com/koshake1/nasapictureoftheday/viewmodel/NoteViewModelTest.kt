package com.koshake1.nasapictureoftheday.viewmodel


import com.koshake1.nasapictureoftheday.data.notes.NotesData
import com.koshake1.nasapictureoftheday.data.notes.NotesRepositoryImpl
import com.koshake1.nasapictureoftheday.ui.notes.NoteViewModel
import com.koshake1.nasapictureoftheday.utils.DELETE_ERROR
import com.koshake1.nasapictureoftheday.utils.DELETE_SUCCESS
import com.koshake1.nasapictureoftheday.utils.SAVING_ERROR
import com.koshake1.nasapictureoftheday.utils.SAVING_SUCCESS
import com.nhaarman.mockito_kotlin.verify
import org.junit.*
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.never
import org.mockito.internal.verification.VerificationModeFactory.times
import java.lang.RuntimeException

class NoteViewModelTest {

    private lateinit var viewModel: NoteViewModel

    private val currentNote = NotesData(title = "Hello!")

    private val notesRepositoryMock = Mockito.mock(NotesRepositoryImpl::class.java)


    @Before
    fun setUp() {
        viewModel = NoteViewModel(notesRepositoryMock, currentNote)
    }

    @Test
    fun `ViewModel Note title changed`() {

        viewModel.updateTitle("New")

        Assert.assertEquals("New", viewModel.note?.title)
    }

    @Test
    fun `ViewModel Note not null after update`() {

        viewModel.updateTitle("New")

        Assert.assertNotNull(viewModel.note)
    }

    @Test
    fun `ViewModel Note text changed`() {

        viewModel.updateNote("New text")

        Assert.assertNotEquals("Text", viewModel.note?.note)
    }

    @Test
    fun `View model save notes shows successful message`() {

        viewModel.saveNote()

        Assert.assertEquals(viewModel.showError(), SAVING_SUCCESS)
    }

    @Test
    fun `View model delete notes shows successful message`() {

        viewModel.saveNote()
        viewModel.deleteNote()

        Assert.assertEquals(viewModel.showError(), DELETE_SUCCESS)
    }

    @Test
    fun `NotesRepositoryImpl addOrReplace called once`() {

        viewModel.saveNote()

        verify(notesRepositoryMock, times(1)).addOrReplaceNote(currentNote)
    }

    @Test
    fun `NotesRepositoryImpl deleteNote never called`() {

        viewModel.saveNote()

        verify(notesRepositoryMock, never()).deleteNote(currentNote)
    }

    @Test
    fun `NotesRepositoryImpl deleteNote called once`() {

        viewModel.deleteNote()

        verify(notesRepositoryMock, times(1)).deleteNote(currentNote)
    }

    @Test
    fun `NoteViewModel deleteNote throws Runtime exception`() {

        `when`(notesRepositoryMock.deleteNote(currentNote)).thenThrow(RuntimeException("Delete note failed!"))
        viewModel.deleteNote()

        Assert.assertEquals(viewModel.showError(), DELETE_ERROR)
    }

    @Test
    fun `NotesViewModel saveNote throws Runtime exception`() {

        `when`(notesRepositoryMock.addOrReplaceNote(currentNote)).thenThrow(RuntimeException("Save note failed!"))
        viewModel.saveNote()

        Assert.assertEquals(viewModel.showError(), SAVING_ERROR)
    }

    @Test
    fun `NotesRepositoryImpl getListForNotify returns List`()
    {
        val notes: MutableList<NotesData> = mutableListOf(NotesData(1, "1", "1"),
            NotesData(2, "2", "2"), NotesData(3, "3", "3"))

        `when`(notesRepositoryMock.getListForNotify()).thenReturn(notes)

        Assert.assertEquals(notesRepositoryMock.getListForNotify(), notes)
    }
}