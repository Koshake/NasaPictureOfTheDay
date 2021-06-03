package com.koshake1.nasapictureoftheday

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T>(
    protected val liveDataForViewToObserve: MutableLiveData<T> = MutableLiveData(),
) : ViewModel() {

    fun getData(date: String): LiveData<T> {
        sendServerRequest(date)
        return liveDataForViewToObserve
    }

    abstract fun sendServerRequest(date: String)
}