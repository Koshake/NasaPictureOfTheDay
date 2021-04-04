package com.koshake1.nasapictureoftheday.retrofit.data

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.koshake1.nasapictureoftheday.retrofit.api.IPictureOfTheDayApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class PODRetrofitImpl : RetrofitImpl() {

    fun getRetrofitImpl(): IPictureOfTheDayApi {
        val podRetrofit = Retrofit.Builder()
            .baseUrl(super.baseUrl)
            .addConverterFactory(GsonConverterFactory.create(super.gson))
            .client(super.createOkHttpClient(super.PODInterceptor()))
            .build()
        return podRetrofit.create(IPictureOfTheDayApi::class.java)
    }
}