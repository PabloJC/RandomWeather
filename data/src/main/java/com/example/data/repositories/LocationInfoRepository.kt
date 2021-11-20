package com.example.data.repositories

import com.example.domain.DomainError
import com.example.domain.Either
import com.example.domain.models.LocationInfo

interface LocationInfoRepository {
    suspend fun getNewLocationInfo(): Either<DomainError, LocationInfo>
}