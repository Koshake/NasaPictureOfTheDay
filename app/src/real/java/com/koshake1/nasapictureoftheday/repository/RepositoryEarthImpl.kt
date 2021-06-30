package com.koshake1.nasapictureoftheday.repository

import com.koshake1.nasapictureoftheday.retrofit.data.EarthServerResponseData
import com.koshake1.nasapictureoftheday.retrofit.data.RetrofitImpl

class RepositoryEarthImpl(private val retrofitImpl: RetrofitImpl) : RepositoryEarth {

    override suspend fun sendServerRequest(date: String, apiKey: String): List<EarthServerResponseData> {
        return retrofitImpl.getRetrofitImpl().getEarthDateAsync(date, apiKey).await()
    }
}