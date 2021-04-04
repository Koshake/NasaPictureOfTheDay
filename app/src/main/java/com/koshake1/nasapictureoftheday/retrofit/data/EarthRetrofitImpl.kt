package com.koshake1.nasapictureoftheday.retrofit.data

import com.koshake1.nasapictureoftheday.retrofit.api.IEarthApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EarthRetrofitImpl : RetrofitImpl() {

    fun getRetrofitImpl(): IEarthApi {
        val earthRetrofit = Retrofit.Builder()
            .baseUrl(super.baseUrl)
            .addConverterFactory(GsonConverterFactory.create(super.gson))
            .client(super.createOkHttpClient(super.PODInterceptor()))
            .build()
        return earthRetrofit.create(IEarthApi::class.java)
    }
}