package com.example.randomweather.data.utils

import android.location.Location
import com.example.domain.models.Coordinates
import com.example.domain.models.LocationInfo
import com.example.domain.models.Weather
import com.example.randomweather.data.remote.retrofit.responses.WeatherDataResponse
import com.example.randomweather.data.remote.retrofit.responses.WeatherResponse
import com.google.android.gms.maps.model.LatLng

fun Coordinates.toLocation() = Location("").also {
    it.latitude = latitude
    it.longitude = longitude
}

fun Location.toCoordinates() = Coordinates(latitude, longitude)

fun LocationInfo.getLatLng() = coordinates?.run { LatLng(latitude, longitude) }

fun WeatherResponse.toWeather() = Weather(icon, description)

fun WeatherDataResponse.toLocationInfo(): LocationInfo {
    return LocationInfo(name,
                        coordinates?.run { Coordinates(latitude, longitude) },
                        weather.map { it.toWeather() },
                        main?.temperature,
                        main?.minTemperature,
                        main?.maxTemperature,
                        main?.pressure,
                        main?.humidity
    )
}
