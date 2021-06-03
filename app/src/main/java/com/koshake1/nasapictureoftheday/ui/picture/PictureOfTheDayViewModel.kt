package com.koshake1.nasapictureoftheday.ui.picture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.koshake1.nasapictureoftheday.BaseViewModel
import com.koshake1.nasapictureoftheday.BuildConfig
import com.koshake1.nasapictureoftheday.data.POD.PictureOfTheDayData
import com.koshake1.nasapictureoftheday.retrofit.data.PODRetrofitImpl
import com.koshake1.nasapictureoftheday.retrofit.data.PODServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel(
    private val retrofitImpl: PODRetrofitImpl
) : BaseViewModel<PictureOfTheDayData>() {

    override fun sendServerRequest(date: String) {
        liveDataForViewToObserve.value = PictureOfTheDayData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PictureOfTheDayData.Error(Throwable("You need api key!"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDayByDate(apiKey, date).enqueue(object :
                Callback<PODServerResponseData> {
                override fun onResponse(
                    call: Call<PODServerResponseData>,
                    response: Response<PODServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value =
                            PictureOfTheDayData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                PictureOfTheDayData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                PictureOfTheDayData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                    liveDataForViewToObserve.value = PictureOfTheDayData.Error(t)
                }
            })
        }
    }
}