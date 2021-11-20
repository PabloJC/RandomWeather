package com.example.testshared

import com.example.data.datasources.LocationInfoRemoteDatasource
import com.example.domain.DomainError
import com.example.domain.Either
import com.example.domain.models.Coordinates
import com.example.domain.models.LocationInfo
import javax.inject.Inject

class FakeLocationInfoRemoteDatasource @Inject constructor() : LocationInfoRemoteDatasource {

    override suspend fun getLocationInfo(location: Coordinates): Either<DomainError, LocationInfo> = Either.Right(
            mockLocationInfo
    )

}
