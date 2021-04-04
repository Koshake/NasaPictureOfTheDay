package com.koshake1.nasapictureoftheday.utils

import android.content.SharedPreferences
import android.view.Gravity
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import coil.api.load
import com.koshake1.nasapictureoftheday.R
import kotlinx.android.synthetic.main.main_fragment.*

fun Fragment.toast(string: String?) {
    Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.BOTTOM, 0, 0)
        show()
    }
}

fun AppCompatActivity.setNewTheme(newTheme: Int) {
    when (newTheme) {
        Themes.YELLOW.themeNum -> setTheme(R.style.AppTheme_AppThemeYellow)
        Themes.BLUE.themeNum -> setTheme(R.style.AppTheme_AppThemeBlue)
        Themes.FANCY.themeNum -> setTheme(R.style.AppThemeFancy)
    }
}

fun AppCompatActivity.resetTheme() {
    val sharedPref: SharedPreferences = getSharedPreferences(THEME_TAG, PRIVATE_MODE)
    if (sharedPref.contains(THEME_TAG)) {
        setNewTheme(sharedPref.getInt(THEME_TAG, Themes.YELLOW.themeNum))
    } else {
        setNewTheme(Themes.YELLOW.themeNum)
    }
}