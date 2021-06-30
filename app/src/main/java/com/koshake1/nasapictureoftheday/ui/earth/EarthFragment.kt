package com.koshake1.nasapictureoftheday.ui.earth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.koshake1.nasapictureoftheday.R
import com.koshake1.nasapictureoftheday.data.earth.EarthData
import com.koshake1.nasapictureoftheday.retrofit.data.EarthServerResponseData
import com.koshake1.nasapictureoftheday.ui.earth.adapter.EarthRecyclerAdapter
import com.koshake1.nasapictureoftheday.ui.earth.adapter.OnListItemClickListener
import com.koshake1.nasapictureoftheday.utils.toast
import kotlinx.android.synthetic.main.fragment_earth.*
import org.koin.android.scope.currentScope

class EarthFragment(private val date: String) : Fragment() {

    companion object {
        val TAG = "EARTH_TAG"
    }

    lateinit var viewModel: EarthViewModel

    private var adapter: EarthRecyclerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "Earth onCreate view ")
        initViewModel()
        return inflater.inflate(R.layout.fragment_earth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    fun getDate() = date

    private fun initViewModel() {
        val model: EarthViewModel by currentScope.inject()
        viewModel = model
        viewModel.subscribeToLiveData()
            .observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.handleServerRequest(date)
    }

    private fun renderData(data: EarthData?) {
        when (data) {
            is EarthData.Success -> {
                val serverResponseData = data.serverResponseData
                serverResponseData?.let {
                    renderLoadingStop()
                    renderPicture(serverResponseData)
                }
            }
            is EarthData.Loading -> {
                renderLoadingStart()
            }
            is EarthData.Error -> {
                renderLoadingStop()
                toast(data.error.message)
            }
        }
    }

    private fun renderPicture(data: List<EarthServerResponseData>) {
        adapter?.listOfPictures = data
    }

    private fun renderLoadingStart() {
        progressBarEarth.visibility = View.VISIBLE
    }

    private fun renderLoadingStop() {
        progressBarEarth.visibility = View.GONE
    }

    private fun initAdapter() {
        rv_earth.layoutManager = LinearLayoutManager(requireActivity())
        adapter = EarthRecyclerAdapter(object : OnListItemClickListener {
            override fun onItemClick() {
                Toast.makeText(context, "Item Clicked", Toast.LENGTH_SHORT).show()
            }
        })
        rv_earth.adapter = adapter
    }
}