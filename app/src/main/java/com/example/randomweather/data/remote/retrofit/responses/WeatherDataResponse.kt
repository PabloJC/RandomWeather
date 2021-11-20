package com.example.randomweather.data.remote.retrofit.responses

import com.example.randomweather.data.remote.retrofit.api.CORDINATES_PARAM
import com.example.randomweather.data.remote.retrofit.api.ID_PARAM
import com.example.randomweather.data.remote.retrofit.api.MAIN_PARAM
import com.example.randomweather.data.remote.retrofit.api.NAME_PARAM
import com.example.randomweather.data.remote.retrofit.api.WEATHER_PARAM
import com.google.gson.annotations.SerializedName

data class WeatherDataResponse(
        @SerializedName(CORDINATES_PARAM) val coordinates: WeatherCoordinatesResponse?,
        @SerializedName(WEATHER_PARAM) val weather: List<WeatherResponse>,
        @SerializedName(MAIN_PARAM) val main: WeatherMainResponse?,
        @SerializedName(ID_PARAM) val id: Long?,
        @SerializedName(NAME_PARAM) val name: String?,
)