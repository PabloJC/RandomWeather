package com.example.testshared

import com.example.domain.models.Coordinates
import com.example.domain.models.LocationInfo
import com.example.domain.models.Weather

val mockCoordinates = Coordinates(42.0000, -3.0000)
val mockCoordinates2 = Coordinates(43.0000, -2.0000)

val mockWeather = Weather("icon", "description")
val mockWeatherList = listOf(mockWeather)

val mockLocationInfo = LocationInfo("name", mockCoordinates, mockWeatherList, 1.0, 1.0, 1.0, 1, 1)