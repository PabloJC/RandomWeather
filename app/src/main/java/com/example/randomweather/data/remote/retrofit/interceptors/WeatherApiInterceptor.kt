package com.example.randomweather.data.remote.retrofit.interceptors

import com.example.randomweather.data.remote.retrofit.api.APP_ID_PARAM
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class WeatherApiInterceptor @Inject constructor(private val appId: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request().run {
            val httpUrl = url().newBuilder().addQueryParameter(APP_ID_PARAM, appId).build()
            newBuilder().url(httpUrl).build()
        })
    }
}