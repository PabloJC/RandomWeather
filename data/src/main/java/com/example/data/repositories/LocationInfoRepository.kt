package com.example.data.repositories

import com.example.domain.DomainError
import com.example.domain.Either
import com.example.domain.models.LocationInfo
import kotlinx.coroutines.flow.Flow

interface LocationInfoRepository {
    val locationInfoFlow: Flow<LocationInfo>
    suspend fun getNewLocationInfo(): Either<DomainError, LocationInfo>
}