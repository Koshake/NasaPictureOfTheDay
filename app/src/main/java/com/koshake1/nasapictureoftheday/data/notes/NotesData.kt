package com.koshake1.nasapictureoftheday.data.notes

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NotesData(
    val id: Long = noteId,
    val title: String = "",
    val note: String = ""
) : Parcelable
