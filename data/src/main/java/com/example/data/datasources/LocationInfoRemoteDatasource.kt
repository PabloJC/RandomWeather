package com.example.data.datasources

import com.example.domain.DomainError
import com.example.domain.Either
import com.example.domain.models.Coordinates
import com.example.domain.models.LocationInfo

interface LocationInfoRemoteDatasource {
    suspend fun getLocationInfo(location: Coordinates): Either<DomainError, LocationInfo>
}
