package com.koshake1.nasapictureoftheday.ui.notes

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.core.widget.addTextChangedListener
import com.koshake1.nasapictureoftheday.R
import com.koshake1.nasapictureoftheday.data.notes.NotesData
import com.koshake1.nasapictureoftheday.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_note_add.*
import org.koin.android.scope.currentScope
import org.koin.core.parameter.parametersOf

class NoteFragment : Fragment(R.layout.fragment_note_add) {

    companion object {
        const val NOTE_KEY = "Note"

        fun newInstance(note: NotesData? = null): NoteFragment {
            val fragment = NoteFragment()
            val arguments = Bundle()
            arguments.putParcelable(NOTE_KEY, note)
            fragment.arguments = arguments

            return fragment
        }
    }

    private val note: NotesData? by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getParcelable<NotesData>(
            NOTE_KEY
        )
    }

    lateinit var viewModel : NoteViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolBar(view)
        initViewModel()

        viewModel.note?.let {
            titleEt.setText(it.title)
            bodyEt.setText(it.note)
        }

        titleEt.addTextChangedListener {
            viewModel.updateTitle(it?.toString() ?: "")
        }

        bodyEt.addTextChangedListener {
            viewModel.updateNote(it?.toString() ?: "")
        }

        buttonSave.setOnClickListener {
            viewModel.saveNote()
            view?.let { it.hideKeyboard() }
            activity?.onBackPressed()
        }
    }

    private fun setToolBar(view: View) {
        val context = activity as NotesActivity
        context.setSupportActionBar(view.findViewById(R.id.toolbar_note))
        context.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_note.setNavigationOnClickListener {
            (activity as AppCompatActivity)?.onBackPressed()
        }
    }

    private fun initViewModel() {

        val model: NoteViewModel by currentScope.inject {
            parametersOf(note)
        }
        viewModel = model
    }
}
