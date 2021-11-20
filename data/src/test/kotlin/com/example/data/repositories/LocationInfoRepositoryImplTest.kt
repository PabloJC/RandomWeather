package com.example.data.repositories

import com.example.data.datasources.LocationInfoLocalDatasource
import com.example.data.datasources.LocationInfoRemoteDatasource
import com.example.data.utils.LocationProvider
import com.example.domain.Either
import com.example.domain.NoDataFound
import com.example.testshared.mockCoordinates
import com.example.testshared.mockCoordinates2
import com.example.testshared.mockLocationInfo
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LocationInfoRepositoryImplTest {
    private val remoteDatasource: LocationInfoRemoteDatasource = mockk()
    private val localDatasource: LocationInfoLocalDatasource = mockk()
    private val locationProvider: LocationProvider = mockk()

    private val repository = LocationInfoRepositoryImpl(remoteDatasource, localDatasource, locationProvider)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `WHEN getNewLocationInfo, GIVEN existing currentLocation, THEN should return location info from random location`() {
        coEvery { localDatasource.currentCoordinates } returns mockCoordinates
        coEvery { localDatasource.currentCoordinates = any() } just Runs
        coEvery { locationProvider.getRandomLocation(any()) } returns Either.Right(mockCoordinates2)
        coEvery { remoteDatasource.getLocationInfo(any()) } returns Either.Right(mockLocationInfo)
        val result = runBlocking {
            repository.getNewLocationInfo()
        }
        coVerify { localDatasource.currentCoordinates = mockCoordinates2 }
        assertEquals(Either.Right(mockLocationInfo), result)
    }

    @Test
    fun `WHEN getNewLocationInfo, GIVEN without currentLocation, THEN should return location info from current location`() {
        coEvery { localDatasource.currentCoordinates } returns null
        coEvery { localDatasource.currentCoordinates = any() } just Runs
        coEvery { locationProvider.getCurrentLocation() } returns Either.Right(mockCoordinates)
        coEvery { remoteDatasource.getLocationInfo(any()) } returns Either.Right(mockLocationInfo)
        val result = runBlocking {
            repository.getNewLocationInfo()
        }
        coVerify { localDatasource.currentCoordinates = mockCoordinates }
        assertEquals(Either.Right(mockLocationInfo), result)
    }

    @Test
    fun `WHEN getNewLocationInfo, GIVEN without currentLocation, THEN should return error`() {
        coEvery { localDatasource.currentCoordinates } returns null
        coEvery { locationProvider.getCurrentLocation() } returns Either.Right(mockCoordinates)
        coEvery { remoteDatasource.getLocationInfo(any()) } returns Either.Left(NoDataFound)
        val result = runBlocking {
            repository.getNewLocationInfo()
        }
        assertEquals(Either.Left(NoDataFound), result)
    }
}