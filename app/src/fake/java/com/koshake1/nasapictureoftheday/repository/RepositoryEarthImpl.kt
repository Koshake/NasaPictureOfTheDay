package com.koshake1.nasapictureoftheday.repository

import com.koshake1.nasapictureoftheday.data.earth.EarthData
import com.koshake1.nasapictureoftheday.retrofit.data.EarthServerResponseData
import com.koshake1.nasapictureoftheday.retrofit.data.PODServerResponseData
import com.koshake1.nasapictureoftheday.retrofit.data.RetrofitImpl
import java.time.LocalDate
import kotlin.random.Random

class RepositoryEarthImpl(private val retrofitImpl: RetrofitImpl) : RepositoryEarth {

    override suspend fun sendServerRequest(date: String, apiKey: String): List<EarthServerResponseData> {
        return generateResponse()
    }

    private fun generateResponse() : List<EarthServerResponseData> {
        val list: MutableList<EarthServerResponseData> = mutableListOf()
        for (index in 1..100) {
            list.add(
                EarthServerResponseData(
                    identifier = "1",
                    caption = "Fake",
                    version = "01",
                    centroid_coordinates = null,
                    dscovr_j2000_position = null,
                    lunar_j2000_position = null,
                    sun_j2000_position = null,
                    attitude_quaternions = null,
                    date = "2021-06-27 00:59:48",
                    image = "epic_1b_20210626004554"
                )
            )
        }
        return list
    }
}