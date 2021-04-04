package com.koshake1.nasapictureoftheday.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.koshake1.nasapictureoftheday.R
import com.koshake1.nasapictureoftheday.utils.PRIVATE_MODE
import com.koshake1.nasapictureoftheday.utils.THEME_TAG
import com.koshake1.nasapictureoftheday.utils.Themes
import com.koshake1.nasapictureoftheday.utils.setNewTheme

class SettingsActivity : AppCompatActivity() {
    companion object {
        const val TAG = "settings"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "SettingsActivity onCreate ")

        val sharedPref: SharedPreferences = getSharedPreferences(THEME_TAG, PRIVATE_MODE)
        if (sharedPref.contains(THEME_TAG)) {
            setNewTheme(sharedPref.getInt(THEME_TAG, Themes.YELLOW.themeNum))
        } else {
            setNewTheme(Themes.YELLOW.themeNum)
        }

        setContentView(R.layout.activity_settings)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_settings, SettingsFragment())
                .commit()
        }
    }
}