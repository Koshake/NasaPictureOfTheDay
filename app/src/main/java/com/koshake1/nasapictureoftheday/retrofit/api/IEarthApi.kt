package com.koshake1.nasapictureoftheday.retrofit.api

import com.koshake1.nasapictureoftheday.retrofit.data.EarthServerResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IEarthApi {
    @GET("/EPIC/api/natural/date/{date}")
    fun getEarthDate(
        @Path("date") date: String,
        @Query("api_key") apiKey: String
    ): Call<List<EarthServerResponseData>>
}