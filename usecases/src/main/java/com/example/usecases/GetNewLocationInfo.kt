package com.example.usecases

import com.example.data.repositories.LocationInfoRepository
import javax.inject.Inject

class GetNewLocationInfo @Inject constructor(private val repository: LocationInfoRepository) {
    suspend operator fun invoke() = repository.getNewLocationInfo()
    val flowResult = repository.locationInfoFlow
}