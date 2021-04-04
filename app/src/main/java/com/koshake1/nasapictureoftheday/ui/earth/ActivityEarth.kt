package com.koshake1.nasapictureoftheday.ui.earth

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.koshake1.nasapictureoftheday.R
import com.koshake1.nasapictureoftheday.utils.*
import kotlinx.android.synthetic.main.activity_earth.*

class ActivityEarth : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resetTheme()
        setContentView(R.layout.activity_earth)
        view_pager.adapter = EarthViewPagerAdapter(supportFragmentManager)
        tab_layout.setupWithViewPager(view_pager)

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                resetIndicators(position)
            }
        })
    }

    private fun resetIndicators(num : Int) {
        when (num) {
            POSITION_FIRST -> {
                indicator_first.setImageResource(R.drawable.swipe_indicator_active)
                indicator_second.setImageResource(R.drawable.swipe_indicator_passive)
                indicator_third.setImageResource(R.drawable.swipe_indicator_passive)
            }
            POSITION_SECOND -> {
                indicator_second.setImageResource(R.drawable.swipe_indicator_active)
                indicator_first.setImageResource(R.drawable.swipe_indicator_passive)
                indicator_third.setImageResource(R.drawable.swipe_indicator_passive)
            }
            POSITION_THIRD -> {
                indicator_third.setImageResource(R.drawable.swipe_indicator_active)
                indicator_first.setImageResource(R.drawable.swipe_indicator_passive)
                indicator_second.setImageResource(R.drawable.swipe_indicator_passive)
        }
            else -> {
                indicator_first.setImageResource(R.drawable.swipe_indicator_active)
                indicator_second.setImageResource(R.drawable.swipe_indicator_passive)
                indicator_third.setImageResource(R.drawable.swipe_indicator_passive)
            }
        }
    }
}