package com.koshake1.nasapictureoftheday.repository

import com.koshake1.nasapictureoftheday.retrofit.data.PODServerResponseData

interface RepositoryPOD {
    suspend fun sendServerRequest(date: String, apiKey: String) : PODServerResponseData

}