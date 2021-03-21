package com.koshake1.nasapictureoftheday.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.koshake1.nasapictureoftheday.R
import com.koshake1.nasapictureoftheday.ui.picture.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }
}