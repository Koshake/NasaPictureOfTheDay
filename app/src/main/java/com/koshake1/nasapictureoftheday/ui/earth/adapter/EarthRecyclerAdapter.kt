package com.koshake1.nasapictureoftheday.ui.earth.adapter

import android.content.Context
import android.transition.ChangeImageTransform
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.koshake1.nasapictureoftheday.R
import com.koshake1.nasapictureoftheday.retrofit.data.EarthServerResponseData
import com.koshake1.nasapictureoftheday.ui.notes.adapter.ItemTouchHelperAdapter
import kotlinx.android.synthetic.main.item_earth.view.*

class EarthRecyclerAdapter(val onItemClickListener: OnListItemClickListener) :
    RecyclerView.Adapter<EarthRecyclerAdapter.ViewHolder>(), ItemTouchHelperAdapter {

    companion object {
        const val TAG = "1111"
    }

    lateinit var context: Context

    var listOfPictures: List<EarthServerResponseData> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_earth, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listOfPictures.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfPictures[position])
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
    }

    override fun onItemDismiss(position: Int) {
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var isImageExpanded = false

        fun bind(serverResponseData: EarthServerResponseData) {
            val date = serverResponseData.date?.take(10)
            val dateWithDiv = date?.replace("-", "/")
            val url = "https://epic.gsfc.nasa.gov/archive/natural/$dateWithDiv/" +
                    "jpg/${serverResponseData.image}.jpg "
            Log.d(TAG, "EarthRecyclerAdapter bind $url ")
            itemView.earth_image.load(url) {
                crossfade(true)
                error(R.drawable.ic_load_error_vector)
                placeholder(R.drawable.ic_no_photo_vector)
            }
            itemView.earth_image.setOnClickListener {
                isImageExpanded = !isImageExpanded
                TransitionManager.beginDelayedTransition(
                    itemView.earth_item_layout, TransitionSet()
                        .addTransition(ChangeImageTransform())
                )
                val params: ViewGroup.LayoutParams = itemView.earth_image.layoutParams
                itemView.earth_image.layoutParams = params
                itemView.earth_image.scaleType =
                    if (isImageExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.CENTER
            }
            itemView.text_time.text = "Time: ${getImageTime(serverResponseData.date)}"
            itemView.text_description.text = serverResponseData.caption
        }

        private fun getImageTime(date: String?): CharSequence? {
            return date?.removeRange(0, 11)
        }
    }
}