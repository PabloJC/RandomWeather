package com.example.randomweather.data.remote.retrofit.responses

import com.example.randomweather.data.remote.retrofit.api.DESCRIPTION_PARAM
import com.example.randomweather.data.remote.retrofit.api.ICON_PARAM
import com.google.gson.annotations.SerializedName

data class WeatherResponse (
        @SerializedName(DESCRIPTION_PARAM) val description: String?,
        @SerializedName(ICON_PARAM) val icon: String?
)