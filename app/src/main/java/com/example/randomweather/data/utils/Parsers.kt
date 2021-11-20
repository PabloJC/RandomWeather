package com.example.randomweather.data.utils

import android.location.Location
import com.example.domain.models.Coordinates
import com.example.domain.models.LocationInfo
import com.example.randomweather.BuildConfig
import com.example.randomweather.data.remote.retrofit.responses.WeatherDataResponse
import com.google.android.gms.maps.model.LatLng

fun Coordinates.toLocation() = Location("").also {
    it.latitude = latitude
    it.longitude = longitude
}

fun Location.toCoordinates() = Coordinates(latitude, longitude)

fun LocationInfo.getLatLng() = coordinates?.run { LatLng(latitude, longitude) }

fun WeatherDataResponse.toLocationInfo(): LocationInfo {
    val (weatherIcon, weatherDescription) = weather.firstOrNull()?.run { icon to description } ?: null to null
    return LocationInfo(name,
                        coordinates?.run { Coordinates(latitude, longitude) },
                        weatherIcon?.let { "${BuildConfig.ICON_HOST}/img/wn/$it@2x.png" },
                        weatherDescription?.capitalize(),
                        main?.temperature,
                        main?.humidity
    )
}
