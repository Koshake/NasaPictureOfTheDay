package com.koshake1.nasapictureoftheday.retrofit.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.koshake1.nasapictureoftheday.retrofit.api.IPictureOfTheDayApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PODRetrofitImpl : RetrofitImpl() {

    override  fun getRetrofitImpl(): IPictureOfTheDayApi {
        val podRetrofit = Retrofit.Builder()
            .baseUrl(super.baseUrl)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(super.gson))
            .client(super.createOkHttpClient(super.PODInterceptor()))
            .build()
        return podRetrofit.create(IPictureOfTheDayApi::class.java)
    }
}