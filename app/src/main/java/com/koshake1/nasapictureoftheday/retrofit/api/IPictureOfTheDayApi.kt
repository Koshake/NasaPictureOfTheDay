package com.koshake1.nasapictureoftheday.retrofit.api

import com.koshake1.nasapictureoftheday.retrofit.data.EarthServerResponseData
import com.koshake1.nasapictureoftheday.retrofit.data.PODServerResponseData
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IPictureOfTheDayApi {

    @GET("planetary/apod")
    fun getPictureOfTheDayByDateAsync(
        @Query("api_key") apiKey: String,
        @Query("date") date: String
    ): Deferred<PODServerResponseData>

    @GET("/EPIC/api/natural/date/{date}")
    fun getEarthDateAsync(
        @Path("date") date: String,
        @Query("api_key") apiKey: String
    ): Deferred<List<EarthServerResponseData>>
}