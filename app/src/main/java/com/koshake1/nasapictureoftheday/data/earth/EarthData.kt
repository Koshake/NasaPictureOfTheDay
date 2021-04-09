package com.koshake1.nasapictureoftheday.data.earth

import com.koshake1.nasapictureoftheday.retrofit.data.EarthServerResponseData

sealed class EarthData {
    data class Success(val serverResponseData: List<EarthServerResponseData>) : EarthData()
    data class Error(val error: Throwable) : EarthData()
    data class Loading(val progress: Int?) : EarthData()
}