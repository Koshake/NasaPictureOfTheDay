package com.koshake1.nasapictureoftheday.retrofit.data

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.koshake1.nasapictureoftheday.retrofit.api.IPictureOfTheDayApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

abstract class RetrofitImpl {
    protected val baseUrl = "https://api.nasa.gov/"

    val gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    protected fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }

    inner class PODInterceptor : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            return chain.proceed(chain.request())
        }
    }

    abstract fun getRetrofitImpl() : IPictureOfTheDayApi
}