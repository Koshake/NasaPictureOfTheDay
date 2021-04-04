package com.koshake1.nasapictureoftheday.ui.earth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import coil.api.load
import com.koshake1.nasapictureoftheday.R
import com.koshake1.nasapictureoftheday.data.EarthData
import com.koshake1.nasapictureoftheday.utils.toast
import kotlinx.android.synthetic.main.activity_earth.*
import kotlinx.android.synthetic.main.fragment_earth.*
import kotlinx.android.synthetic.main.main_fragment.*
import java.time.LocalDate

class EarthFragment(private val date: String) : Fragment() {

    private val viewModel: EarthViewModel by lazy {
        ViewModelProviders.of(this).get(EarthViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData(date)
            .observe(viewLifecycleOwner, Observer { renderData(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_earth, container, false)
    }

    fun getDate() = date

    private fun renderData(data: EarthData?) {
        when (data) {
            is EarthData.Success -> {
                val serverResponseData = data.serverResponseData
                serverResponseData?.let {
                    loadPicture(serverResponseData[0].image, date)
                    setDescription(serverResponseData[0].caption)
                }
            }
            is EarthData.Loading -> {
            }
            is EarthData.Error -> {
                toast(data.error.message)
            }
        }
    }

    private fun loadPicture(name: String?, date: String?) {
        val newDate = date?.replace('-', '/')
        val url = "https://epic.gsfc.nasa.gov/archive/natural/$newDate/png/$name.png"
        if (url.isNullOrEmpty()) {
            toast("Url is Null")
        } else {
            earth_image.load(url) {
                lifecycle(this@EarthFragment)
                error(R.drawable.ic_load_error_vector)
                placeholder(R.drawable.ic_no_photo_vector)
                //toast("Picture is loaded")
            }
        }
    }

    private fun setDescription(description: String?) {
        image_description?.text = description
    }
}