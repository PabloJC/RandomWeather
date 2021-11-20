package com.example.data.utils

import com.example.domain.DomainError
import com.example.domain.Either
import com.example.domain.models.Coordinates

interface LocationProvider {
    suspend fun getCurrentLocation(): Either<DomainError, Coordinates>
    suspend fun getRandomLocation(location: Coordinates): Either<DomainError, Coordinates>
}