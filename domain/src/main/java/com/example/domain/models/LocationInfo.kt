package com.example.domain.models

data class LocationInfo(
        val name: String?,
        val coordinates: Coordinates?,
        val weatherIconUrl: String?,
        val weatherDescription: String?,
        val temperature: Double?,
        val humidity: Long?
)