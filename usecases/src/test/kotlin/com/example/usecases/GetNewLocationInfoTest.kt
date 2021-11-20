package com.example.usecases

import com.example.data.repositories.LocationInfoRepository
import com.example.domain.DomainError
import com.example.domain.Either
import com.example.domain.NoDataFound
import com.example.domain.models.LocationInfo
import com.example.testshared.mockLocationInfo
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetNewLocationInfoTest {

    private val repository: LocationInfoRepository = mockk()

    private val usecase = GetNewLocationInfo(repository)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `WHEN invoke, THEN returns LocationInfo`() {
        checkResult(Either.Right(mockLocationInfo))
    }

    @Test
    fun `WHEN invoke, THEN returns DomainError`() {
        checkResult(Either.Left(NoDataFound))
    }

    private fun checkResult(expectedResult: Either<DomainError, LocationInfo>) {
        coEvery { repository.getNewLocationInfo() } returns expectedResult

        val result = runBlocking { usecase.invoke() }

        assertEquals(expectedResult, result)
        coVerify { repository.getNewLocationInfo() }
    }
}