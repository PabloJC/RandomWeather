package com.example.randomweather.ui.map

import androidx.lifecycle.Observer
import com.example.domain.DomainError
import com.example.domain.Either
import com.example.domain.NoDataFound
import com.example.domain.models.LocationInfo
import com.example.randomweather.common.BaseUnitTest
import com.example.testshared.mockLocationInfo
import com.example.usecases.GetNewLocationInfo
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
class MapViewModelUnitTest : BaseUnitTest() {

    private val getNewLocationInfo: GetNewLocationInfo = mockk()

    private val locationInfoObserver: Observer<LocationInfo> = mockk()
    private val errorObserver: Observer<DomainError> = mockk()

    private val viewmodel = MapViewModel(getNewLocationInfo, dispatcher)

    override fun setUp() {
        super.setUp()
        viewmodel.run {
            currentlocationInfo.observeForever(locationInfoObserver)
            uiErrors.observeForever(errorObserver)
        }
        every { locationInfoObserver.onChanged(any()) } just Runs
        every { errorObserver.onChanged(any()) } just Runs
    }

    @Test
    fun `WHEN getNewLocation, THEN emit LocationInfo`() {
        coEvery { getNewLocationInfo() } returns Either.Right(mockLocationInfo)

        viewmodel.getNewLocation()

        coVerify { locationInfoObserver.onChanged(mockLocationInfo) }
    }

    @Test
    fun `WHEN getNewLocation, THEN emit DomainError`() {
        coEvery { getNewLocationInfo() } returns Either.Left(NoDataFound)

        viewmodel.getNewLocation()

        coVerify { errorObserver.onChanged(NoDataFound) }
    }
}