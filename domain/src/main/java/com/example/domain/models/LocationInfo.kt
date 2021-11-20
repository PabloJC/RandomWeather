package com.example.domain.models

data class LocationInfo(
        val name: String?,
        val coordinates: Coordinates?,
        val weather: List<Weather>,
        val temperature: Double?,
        val minTemperature: Double?,
        val maxTemperature: Double?,
        val pressure: Long?,
        val humidity: Long?
)