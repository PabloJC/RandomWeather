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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

internal class LocationInfoRepositoryImpl @Inject constructor(
        private val remoteDatasource: LocationInfoRemoteDatasource,
        private val localDatasource: LocationInfoLocalDatasource,
        private val locationProvider: LocationProvider,
) : LocationInfoRepository {

    private val mutableLocationInfoFlow = MutableSharedFlow<LocationInfo>()
    override val locationInfoFlow: Flow<LocationInfo> = mutableLocationInfoFlow

    override suspend fun getNewLocationInfo(): Either<DomainError, LocationInfo> {
        return getNewLocation().flatMap { newLocation ->
            remoteDatasource.getLocationInfo(newLocation).map { locationInfo ->
                mutableLocationInfoFlow.emit(locationInfo)
                localDatasource.currentLocation = newLocation
                locationInfo
            }
        }
    }

    private suspend fun getNewLocation(): Either<DomainError, Coordinates> {
        return with(locationProvider) {
            localDatasource.currentLocation?.let { currentLocation ->
                getRandomLocation(currentLocation)
            } ?: getCurrentLocation()
        }
    }
}