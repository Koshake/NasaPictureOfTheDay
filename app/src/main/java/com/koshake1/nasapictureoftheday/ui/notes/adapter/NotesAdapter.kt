package com.koshake1.nasapictureoftheday.ui.notes.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.koshake1.nasapictureoftheday.R
import com.koshake1.nasapictureoftheday.data.notes.NotesData
import com.koshake1.nasapictureoftheday.data.notes.NotesRepositoryImpl
import kotlinx.android.synthetic.main.item_note.view.*

val DIFF_UTIL: DiffUtil.ItemCallback<NotesData> = object : DiffUtil.ItemCallback<NotesData>() {
    override fun areItemsTheSame(oldItem: NotesData, newItem: NotesData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: NotesData, newItem: NotesData): Boolean {
        return true
    }
}

class NotesAdapter(val noteHandler: (NotesData) -> Unit, val dragListener: OnStartDragListener) :
    ListAdapter<NotesData, NotesAdapter.NoteViewHolder>(DIFF_UTIL), ItemTouchHelperAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        NotesRepositoryImpl.replaceNoteByItem(getItem(fromPosition), getItem(toPosition))
        notifyItemMoved(fromPosition, toPosition)
        //NotesRepositoryImpl.notifyChanges()
    }

    override fun onItemDismiss(position: Int) {
        NotesRepositoryImpl.deleteNote(getItem(position))
        notifyItemRemoved(position)
        //NotesRepositoryImpl.notifyChanges()
    }

    inner class NoteViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
    ), ItemTouchHelperViewHolder {
        private lateinit var currentNote: NotesData

        private val clickListener: View.OnClickListener = View.OnClickListener {
            noteHandler(currentNote)
        }

        @SuppressLint("ClickableViewAccessibility")
        fun bind(item: NotesData) {
            currentNote = item
            with(itemView) {
                title.text = item.title
                body.text = item.note
                setOnClickListener(clickListener)
                dragHandleImageView.setOnTouchListener { _, motionEvent ->
                    if (MotionEventCompat.getActionMasked(motionEvent) == MotionEvent.ACTION_DOWN) {
                        dragListener.onStartDrag(this@NoteViewHolder)
                    }
                    false
                }
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(R.attr.note_selected_background)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(R.attr.note_background)
        }
    }
}
