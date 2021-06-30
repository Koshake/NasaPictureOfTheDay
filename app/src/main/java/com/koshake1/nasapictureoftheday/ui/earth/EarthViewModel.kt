package com.koshake1.nasapictureoftheday.ui.earth

import com.koshake1.nasapictureoftheday.BaseViewModel
import com.koshake1.nasapictureoftheday.BuildConfig
import com.koshake1.nasapictureoftheday.data.earth.EarthData
import com.koshake1.nasapictureoftheday.repository.RepositoryEarth
import kotlinx.coroutines.*

class EarthViewModel(
    private val repository: RepositoryEarth
) : BaseViewModel<EarthData>() {


    override fun handleServerRequest(date: String) {
        liveDataForViewToObserve.value = EarthData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            liveDataForViewToObserve.value =
                EarthData.Error(Throwable("You need api key!"))
            return
        }
        viewModelCoroutineScope.launch {
            val result = repository.sendServerRequest(date, apiKey)
            if (result.isEmpty()) {
                EarthData.Error(Throwable("Response is null or unsuccessful!"))
            } else {
                liveDataForViewToObserve.value =
                    EarthData.Success(result)
            }
        }
    }

    override fun handleError(error: Throwable) {
        liveDataForViewToObserve.value =
            EarthData.Error(
                Throwable(
                    error.message ?: "Response is null or unsuccessful!"
                )
            )
    }

    override fun onCleared() {
        super.onCleared()
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

}