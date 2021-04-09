package com.koshake1.nasapictureoftheday.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.koshake1.nasapictureoftheday.R
import com.koshake1.nasapictureoftheday.utils.PRIVATE_MODE
import com.koshake1.nasapictureoftheday.utils.THEME_TAG
import com.koshake1.nasapictureoftheday.utils.Themes
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.chipGroup

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initChips()
        setToolBar(view)
    }

    private fun initChips() {
        val sharedPref: SharedPreferences =
            (activity as AppCompatActivity).getSharedPreferences(THEME_TAG, PRIVATE_MODE)
        if (sharedPref.contains(THEME_TAG)) {
            when (sharedPref.getInt(THEME_TAG, Themes.YELLOW.themeNum)) {
                Themes.YELLOW.themeNum -> chip_light.isChecked = true
                Themes.BLUE.themeNum -> chip_dark.isChecked = true
                Themes.FANCY.themeNum -> chip_fancy.isChecked = true
                else -> chip_light.isChecked = true
            }
        } else {
            chip_light.isChecked = true
        }

        chipGroup.setOnCheckedChangeListener { chipGroup, position ->
            chipGroup.findViewById<Chip>(position)?.let {
                when (it) {
                    chip_light -> {
                        Bundle().putInt(THEME_TAG, R.style.AppTheme_AppThemeYellow)
                        activity?.let { fragmentActivity ->
                            fragmentActivity.recreate()
                            val editor = sharedPref.edit()
                            editor.putInt(THEME_TAG, Themes.YELLOW.themeNum)
                            editor.apply()
                        }
                    }
                    chip_dark -> {
                        Bundle().putInt(THEME_TAG, R.style.AppTheme_AppThemeBlue)
                        activity?.let { fragmentActivity ->
                            fragmentActivity.recreate()
                            val editor = sharedPref.edit()
                            editor.putInt(THEME_TAG, Themes.BLUE.themeNum)
                            editor.apply()
                        }
                    }
                    chip_fancy -> {
                        Bundle().putInt(THEME_TAG, R.style.AppTheme_AppThemeBlue)
                        activity?.let { fragmentActivity ->
                            fragmentActivity.recreate()
                            val editor = sharedPref.edit()
                            editor.putInt(THEME_TAG, Themes.FANCY.themeNum)
                            editor.apply()
                        }
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setToolBar(view: View) {
        val context = activity as SettingsActivity
        context.setSupportActionBar(view.findViewById(R.id.toolbar_settings))
        context.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_settings.setNavigationOnClickListener {
            (activity as AppCompatActivity)?.onBackPressed()
        }
    }
}
