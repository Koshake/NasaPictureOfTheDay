package com.koshake1.nasapictureoftheday.repository


import com.koshake1.nasapictureoftheday.retrofit.data.PODServerResponseData
import com.koshake1.nasapictureoftheday.retrofit.data.RetrofitImpl

class RepositoryPODImpl(private val retrofitImpl: RetrofitImpl) : RepositoryPOD {
    override suspend fun sendServerRequest(date: String, apiKey: String): PODServerResponseData {
        return retrofitImpl.getRetrofitImpl().getPictureOfTheDayByDateAsync(apiKey, date).await()
    }
}