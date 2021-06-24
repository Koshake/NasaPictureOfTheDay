package com.koshake1.nasapictureoftheday.ui.notes

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.koshake1.nasapictureoftheday.R
import com.koshake1.nasapictureoftheday.data.notes.NotesData
import com.koshake1.nasapictureoftheday.data.notes.NotesRepositoryImpl
import com.koshake1.nasapictureoftheday.di.injectDependencies
import com.koshake1.nasapictureoftheday.ui.notes.adapter.ItemTouchHelperCallback
import com.koshake1.nasapictureoftheday.ui.notes.adapter.NotesAdapter
import com.koshake1.nasapictureoftheday.ui.notes.adapter.OnStartDragListener
import com.koshake1.nasapictureoftheday.ui.settings.SettingsActivity
import kotlinx.android.synthetic.main.fragment_note_main.*
import org.koin.android.ext.android.inject
import org.koin.android.scope.currentScope

class NotesFragment : Fragment(R.layout.fragment_note_main) {

    companion object {
        private val TAG = "notesTag"
        fun newInstance() = NotesFragment()
    }

    lateinit var notesViewModel : NotesViewModel

    private val notesRepository : NotesRepositoryImpl by inject()

    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setBottomBar(view)

        initViewModel()

        val adapter =
            NotesAdapter(notesRepository, { navigateToNote(it) }, object : OnStartDragListener {
                override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                    itemTouchHelper.startDrag(viewHolder)
                }
            })

        mainRecycler.adapter = adapter

        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(mainRecycler)

        notesViewModel.observeViewState().observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Value -> {
                    adapter.submitList(it.notes)
                    if (it.notes.isEmpty()) textFirstNote.visibility = View.VISIBLE
                    else textFirstNote.visibility = View.GONE
                }
                ViewState.EMPTY -> {
                    adapter.submitList(null)
                    TransitionManager.beginDelayedTransition(layout_notes)
                    textFirstNote.visibility = View.VISIBLE
                }
            }
        }

        fab_add.setOnClickListener {
            navigateToCreation()
        }

        mainRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fab_add.show()
                } else {
                    fab_add.hide()
                }
            }
        })

        if (notesRepository.getListForNotify().isNotEmpty())
            textFirstNote.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "OnResume Notes Fragment")
        if (notesRepository.getListForNotify().isEmpty()) {
            textFirstNote.visibility = View.VISIBLE
        } else {
            textFirstNote.visibility = View.GONE
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar_notes, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_note_settings ->
                startActivity(Intent(requireActivity(), SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateToNote(note: NotesData) {
        (requireActivity() as NotesActivity).navigateTo(NoteFragment.newInstance(note))
    }

    private fun navigateToCreation() {
        (requireActivity() as NotesActivity).navigateTo(NoteFragment.newInstance(null))
    }

    private fun setBottomBar(view: View) {
        val context = activity as NotesActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar_note))
        bottom_app_bar_note.replaceMenu(R.menu.menu_bottom_bar_notes)
        setHasOptionsMenu(true)
        context.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        bottom_app_bar_note.setNavigationOnClickListener {
            (activity as AppCompatActivity)?.onBackPressed()
        }
    }

    private fun initViewModel() {
        val model: NotesViewModel by currentScope.inject()
        notesViewModel = model
    }
}