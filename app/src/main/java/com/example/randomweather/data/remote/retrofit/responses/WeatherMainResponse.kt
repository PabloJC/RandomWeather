package com.example.randomweather.data.remote.retrofit.responses

import com.example.randomweather.data.remote.retrofit.api.HUMIDITY_PARAM
import com.example.randomweather.data.remote.retrofit.api.MAX_TEMPERATURE_PARAM
import com.example.randomweather.data.remote.retrofit.api.MIN_TEMPERATURE_PARAM
import com.example.randomweather.data.remote.retrofit.api.PRESSURE_PARAM
import com.example.randomweather.data.remote.retrofit.api.TEMPERATURE_PARAM
import com.google.gson.annotations.SerializedName

data class WeatherMainResponse (
        @SerializedName(TEMPERATURE_PARAM) val temperature: Double?,
        @SerializedName(MIN_TEMPERATURE_PARAM) val minTemperature: Double?,
        @SerializedName(MAX_TEMPERATURE_PARAM) val maxTemperature: Double?,
        @SerializedName(PRESSURE_PARAM) val pressure: Long?,
        @SerializedName(HUMIDITY_PARAM) val humidity: Long?
)