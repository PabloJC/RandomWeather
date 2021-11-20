package com.example.randomweather.data.remote.retrofit

import com.example.data.datasources.LocationInfoRemoteDatasource
import com.example.domain.DomainError
import com.example.domain.Either
import com.example.domain.NoDataFound
import com.example.domain.map
import com.example.domain.models.Coordinates
import com.example.domain.models.LocationInfo
import com.example.randomweather.data.remote.retrofit.api.RetrofitApiService
import com.example.randomweather.data.utils.toLocationInfo
import retrofit2.Response
import javax.inject.Inject

class LocationInfoRetrofitDatasource @Inject constructor(private val service: RetrofitApiService): LocationInfoRemoteDatasource {

    override suspend fun getLocationInfo(location: Coordinates): Either<DomainError, LocationInfo> {
        val response = service.getWeatherData(location.latitude, location.longitude)
        return response.getResult().map { it.toLocationInfo() }
    }

    private fun <T> Response<T>.getResult(): Either<DomainError, T>{
        return if(isSuccessful){
            body()?.let { Either.Right(it) } ?: Either.Left(NoDataFound)
        } else Either.Left(NoDataFound)
    }
}