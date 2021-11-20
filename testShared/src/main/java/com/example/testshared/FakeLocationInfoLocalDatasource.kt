package com.example.testshared

import com.example.data.datasources.LocationInfoLocalDatasource
import com.example.domain.models.Coordinates
import javax.inject.Inject

class FakeLocationInfoLocalDatasource @Inject constructor() : LocationInfoLocalDatasource {
    override var currentCoordinates: Coordinates? = mockCoordinates
}
