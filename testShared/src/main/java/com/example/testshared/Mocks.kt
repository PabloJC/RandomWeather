package com.example.testshared

import com.example.domain.models.Coordinates
import com.example.domain.models.LocationInfo
import com.example.domain.models.Weather

val mockLatitude = 42.0000
val mockLongitude = -3.0000

val mockCoordinates = Coordinates(mockLatitude, mockLongitude)

val mockWeather = Weather("icon", "description")
val mockWeatherList = listOf(mockWeather)

val mockLocationInfo = LocationInfo("name", mockCoordinates, mockWeatherList, 1.0, 1.0, 1.0, 1, 1)