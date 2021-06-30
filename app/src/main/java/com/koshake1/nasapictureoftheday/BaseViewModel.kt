package com.koshake1.nasapictureoftheday

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.koshake1.nasapictureoftheday.data.POD.PictureOfTheDayData
import com.koshake1.nasapictureoftheday.data.earth.EarthData
import kotlinx.coroutines.*

abstract class BaseViewModel<T>(
) : ViewModel() {

    protected val liveDataForViewToObserve = MutableLiveData<T>()
    protected val liveData: LiveData<T> = liveDataForViewToObserve
    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable -> handleError(throwable) })

    fun subscribeToLiveData() = liveData

    abstract fun handleServerRequest(date: String)

    protected abstract fun handleError(error: Throwable)

    override fun onCleared() {
        super.onCleared()
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }
}