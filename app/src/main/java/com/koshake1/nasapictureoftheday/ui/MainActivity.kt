package com.koshake1.nasapictureoftheday.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.koshake1.nasapictureoftheday.R
import com.koshake1.nasapictureoftheday.ui.picture.PictureOfTheDayFragment
import com.koshake1.nasapictureoftheday.utils.PRIVATE_MODE
import com.koshake1.nasapictureoftheday.utils.THEME_TAG
import com.koshake1.nasapictureoftheday.utils.Themes
import com.koshake1.nasapictureoftheday.utils.setNewTheme

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "mainTag"
    }

    private var oldTheme: Int = Themes.YELLOW.themeNum

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "MainActivity onCreate ")

        val sharedPref: SharedPreferences = getSharedPreferences(THEME_TAG, PRIVATE_MODE)
        if (sharedPref.contains(THEME_TAG)) {
            oldTheme = sharedPref.getInt(THEME_TAG, oldTheme)
            setNewTheme(oldTheme)
        } else {
            val editor = sharedPref.edit()
            editor.putInt(THEME_TAG, oldTheme)
            editor.apply()
        }
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivity onResume ")
        val sharedPref: SharedPreferences = getSharedPreferences(THEME_TAG, PRIVATE_MODE)
        val newTheme = sharedPref.getInt(THEME_TAG, Themes.YELLOW.themeNum)
        Log.d(TAG, "MainActivity onResume newTheme = $newTheme  oldTheme = $oldTheme ")
        if (newTheme != oldTheme) {
            oldTheme = newTheme
            setNewTheme(oldTheme)
            recreate()
        }
    }


}