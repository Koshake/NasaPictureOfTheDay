package com.koshake1.nasapictureoftheday.repository

import com.koshake1.nasapictureoftheday.data.earth.EarthData
import com.koshake1.nasapictureoftheday.retrofit.data.EarthServerResponseData

interface RepositoryEarth {
    suspend fun sendServerRequest(date: String, apiKei: String) : List<EarthServerResponseData>
}