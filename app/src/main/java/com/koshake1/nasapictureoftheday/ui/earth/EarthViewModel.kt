package com.koshake1.nasapictureoftheday.ui.earth

import com.koshake1.nasapictureoftheday.BaseViewModel
import com.koshake1.nasapictureoftheday.BuildConfig
import com.koshake1.nasapictureoftheday.data.earth.EarthData
import com.koshake1.nasapictureoftheday.retrofit.data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EarthViewModel(
    private val retrofitImpl: PODRetrofitImpl
) : BaseViewModel<EarthData>() {

    override fun sendServerRequest(date: String) {
        liveDataForViewToObserve.value = EarthData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            EarthData.Error(Throwable("You need api key!"))
        } else {
            retrofitImpl.getRetrofitImpl().getEarthDate(date, apiKey).enqueue(object :
                Callback<List<EarthServerResponseData>> {
                override fun onResponse(
                    call: Call<List<EarthServerResponseData>>,
                    response: Response<List<EarthServerResponseData>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value =
                            EarthData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                EarthData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                EarthData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<List<EarthServerResponseData>>, t: Throwable) {
                    liveDataForViewToObserve.value = EarthData.Error(t)
                }
            })
        }
    }
}