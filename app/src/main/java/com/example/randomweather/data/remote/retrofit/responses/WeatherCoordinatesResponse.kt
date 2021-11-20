package com.example.randomweather.data.remote.retrofit.responses

import com.example.randomweather.data.remote.retrofit.api.LATITUDE_PARAM
import com.example.randomweather.data.remote.retrofit.api.LONGITUDE_PARAM
import com.google.gson.annotations.SerializedName

data class WeatherCoordinatesResponse (
        @SerializedName(LONGITUDE_PARAM) val longitude: Double,
        @SerializedName(LATITUDE_PARAM) val latitude: Double
)