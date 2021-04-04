package com.koshake1.nasapictureoftheday.ui.earth

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.koshake1.nasapictureoftheday.utils.POSITION_FIRST
import com.koshake1.nasapictureoftheday.utils.POSITION_SECOND
import com.koshake1.nasapictureoftheday.utils.POSITION_THIRD
import java.time.LocalDate

private const val FIRST_DATE = 0
private const val SECOND_DATE = 1
private const val THIRD_DATE = 2

class EarthViewPagerAdapter(private val fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    private val dates = listOf("2019-05-28", "2019-05-29","2019-05-30")

    private val fragments = arrayOf(
        EarthFragment(dates[FIRST_DATE]),
        EarthFragment(dates[SECOND_DATE]),
        EarthFragment(dates[THIRD_DATE])
    )

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            POSITION_FIRST -> fragments[FIRST_DATE]
            POSITION_SECOND -> fragments[SECOND_DATE]
            POSITION_THIRD -> fragments[THIRD_DATE]
            else -> fragments[FIRST_DATE]
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            POSITION_FIRST -> fragments[FIRST_DATE].getDate()
            POSITION_SECOND -> fragments[SECOND_DATE].getDate()
            POSITION_THIRD -> fragments[THIRD_DATE].getDate()
            else -> fragments[FIRST_DATE].getDate()
        }
    }
}