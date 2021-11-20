package com.example.randomweather.data.remote.retrofit.api

import com.example.randomweather.data.remote.retrofit.responses.WeatherDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApiService {

    @GET("data/$API_VERSION/weather")
    suspend fun getWeatherData(
            @Query(LATITUDE_PARAM) latitude: Double,
            @Query(LONGITUDE_PARAM) longitude: Double,
            @Query(UNITS_PARAM) units: String = UNITS_METRIC_VALUE
    ): Response<WeatherDataResponse>
}