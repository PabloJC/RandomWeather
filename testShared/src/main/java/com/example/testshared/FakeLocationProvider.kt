package com.example.testshared

import com.example.data.utils.LocationProvider
import com.example.domain.DomainError
import com.example.domain.Either
import com.example.domain.models.Coordinates

class FakeLocationProvider : LocationProvider {
    override suspend fun getCurrentLocation(): Either<DomainError, Coordinates> = Either.Right(mockCoordinates)

    override suspend fun getRandomLocation(location: Coordinates): Either<DomainError, Coordinates> = Either.Right(
            mockCoordinates
    )

}