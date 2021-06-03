package com.koshake1.nasapictureoftheday.ui.notes

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.koshake1.nasapictureoftheday.R
import com.koshake1.nasapictureoftheday.ui.MainActivity
import com.koshake1.nasapictureoftheday.utils.*

class NotesActivity : AppCompatActivity() {
    companion object {
        const val TAG = "Notes_activity"
    }

    private var oldTheme: Int = Themes.YELLOW.themeNum

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Notes Activity onCreate ")

        val sharedPref: SharedPreferences = getSharedPreferences(THEME_TAG, PRIVATE_MODE)
        if (sharedPref.contains(THEME_TAG)) {
            oldTheme = sharedPref.getInt(THEME_TAG, oldTheme)
        }
        resetTheme()

        setContentView(R.layout.activity_notes)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_notes, NotesFragment.newInstance())
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "NoteActivity onResume ")
        val sharedPref: SharedPreferences = getSharedPreferences(THEME_TAG, PRIVATE_MODE)
        val newTheme = sharedPref.getInt(THEME_TAG, Themes.YELLOW.themeNum)
        Log.d(MainActivity.TAG, "MainActivity onResume newTheme = $newTheme  oldTheme = $oldTheme ")
        if (newTheme != oldTheme) {
            oldTheme = newTheme
            setNewTheme(oldTheme)
            recreate()
        }
    }

    fun navigateTo(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .add(R.id.container_notes, fragment)
            .addToBackStack("notes")
            .commit()
    }
}