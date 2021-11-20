package com.example.randomweather.data.remote.retrofit.api

import com.example.randomweather.data.remote.retrofit.interceptors.WeatherApiInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RetrofitApiClient @Inject constructor(baseUrl: String, weatherApiInterceptor: WeatherApiInterceptor) {

    private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(weatherApiInterceptor)
            .build()

    val service = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(RetrofitApiService::class.java)
}