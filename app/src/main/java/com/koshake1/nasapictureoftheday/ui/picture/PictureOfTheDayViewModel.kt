package com.koshake1.nasapictureoftheday.ui.picture

import com.koshake1.nasapictureoftheday.BaseViewModel
import com.koshake1.nasapictureoftheday.BuildConfig
import com.koshake1.nasapictureoftheday.data.POD.PictureOfTheDayData
import com.koshake1.nasapictureoftheday.repository.RepositoryPOD
import kotlinx.coroutines.*

class PictureOfTheDayViewModel(
    private val repository: RepositoryPOD
) : BaseViewModel<PictureOfTheDayData>() {

    override fun handleServerRequest(date: String) {
        liveDataForViewToObserve.value = PictureOfTheDayData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            liveDataForViewToObserve.value =
                PictureOfTheDayData.Error(Throwable("You need api key!"))
            return
        }
        viewModelCoroutineScope.launch {
            val result = repository.sendServerRequest(date, apiKey)
            if (result.url == null) {
                liveDataForViewToObserve.value =
                    PictureOfTheDayData.Error(Throwable("Response is null or unsuccessful!"))
            } else {
                liveDataForViewToObserve.value =
                    PictureOfTheDayData.Success(result)
            }
        }
    }

    override fun handleError(error: Throwable) {
        liveDataForViewToObserve.value =
            PictureOfTheDayData.Error(
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