package com.koshake1.nasapictureoftheday.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.koshake1.nasapictureoftheday.R
import com.koshake1.nasapictureoftheday.utils.*

class SettingsActivity : AppCompatActivity() {
    companion object {
        const val TAG = "settings"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "SettingsActivity onCreate ")

        resetTheme()

        setContentView(R.layout.activity_settings)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_settings, SettingsFragment())
                .commit()
        }
    }
}