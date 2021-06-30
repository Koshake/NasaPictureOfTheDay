package com.koshake1.nasapictureoftheday.repository

import com.koshake1.nasapictureoftheday.retrofit.data.PODServerResponseData
import com.koshake1.nasapictureoftheday.retrofit.data.RetrofitImpl

class RepositoryPODImpl(private val retrofitImpl: RetrofitImpl) : RepositoryPOD {

    override suspend fun sendServerRequest(date: String, apiKey: String): PODServerResponseData {
        return generateResponse()
    }

    private fun generateResponse() : PODServerResponseData {
        return PODServerResponseData(
            date = "2021-06-27",
            explanation = "explanation",
            hdurl = "https://apod.nasa.gov/apod/image/2106/neonsaturnaurora_cassini_2560.jpg",
            mediaType = "image",
            title =  "The Dancing Auroras of Saturn",
            url = "https://apod.nasa.gov/apod/image/2106/neonsaturnaurora_cassini_1080.jpg",
            copyright = "copyright"
        )
    }
}