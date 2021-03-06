package com.koshake1.nasapictureoftheday.ui.earth

import android.os.Bundle
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
        setBottomBar()
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }
        })
    }

    private fun setBottomBar() {
        setSupportActionBar(bottom_app_bar_earth)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        bottom_app_bar_earth.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}