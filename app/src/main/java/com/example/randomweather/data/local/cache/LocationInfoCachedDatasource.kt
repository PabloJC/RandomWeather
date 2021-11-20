package com.example.randomweather.data.local.cache

import com.example.data.datasources.LocationInfoLocalDatasource
import com.example.domain.models.Coordinates
import javax.inject.Inject

class LocationInfoCachedDatasource @Inject constructor(): LocationInfoLocalDatasource{
    override var currentLocation: Coordinates? = null
}