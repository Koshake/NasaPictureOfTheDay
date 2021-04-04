package com.koshake1.nasapictureoftheday.ui.picture

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.koshake1.nasapictureoftheday.R
import com.koshake1.nasapictureoftheday.ui.earth.ActivityEarth
import kotlinx.android.synthetic.main.bottom_navigation_layout.*

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_navigation_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navigation_view.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_one -> activity?.let { startActivity(Intent(it, ActivityEarth::class.java)) }
                R.id.navigation_two -> Toast.makeText(context, "Not implemented yet", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }

}