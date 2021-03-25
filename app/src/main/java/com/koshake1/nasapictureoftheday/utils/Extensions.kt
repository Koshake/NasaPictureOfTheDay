package com.koshake1.nasapictureoftheday.utils

import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.koshake1.nasapictureoftheday.R

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