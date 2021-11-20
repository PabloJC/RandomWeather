package com.example.testshared

import com.example.domain.models.Coordinates
import com.example.domain.models.LocationInfo

val mockLatitude = 42.0000
val mockLongitude = -3.0000
val mockCoordinates = Coordinates(mockLatitude, mockLongitude)
val mockCoordinates2 = Coordinates(43.0000, -2.0000)

val mockLocationInfo = LocationInfo("name", mockCoordinates, "", "", 1.0, 1)