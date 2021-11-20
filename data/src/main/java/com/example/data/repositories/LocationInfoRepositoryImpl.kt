package com.example.data.repositories

import com.example.data.datasources.LocationInfoLocalDatasource
import com.example.data.datasources.LocationInfoRemoteDatasource
import com.example.data.utils.LocationProvider
import com.example.domain.DomainError
import com.example.domain.Either
import com.example.domain.flatMap
import com.example.domain.map
import com.example.domain.models.Coordinates
import com.example.domain.models.LocationInfo
import javax.inject.Inject

internal class LocationInfoRepositoryImpl @Inject constructor(
        private val remoteDatasource: LocationInfoRemoteDatasource,
        private val localDatasource: LocationInfoLocalDatasource,
        private val locationProvider: LocationProvider,
) : LocationInfoRepository {

    override suspend fun getNewLocationInfo(): Either<DomainError, LocationInfo> {
        return getNewLocation().flatMap { newLocation ->
            remoteDatasource.getLocationInfo(newLocation).map { locationInfo ->
                localDatasource.currentCoordinates = newLocation
                locationInfo
            }
        }
    }

    private suspend fun getNewLocation(): Either<DomainError, Coordinates> {
        return with(locationProvider) {
            localDatasource.currentCoordinates?.let { currentLocation ->
                getRandomLocation(currentLocation)
            } ?: getCurrentLocation()
        }
    }
}