package com.example.data.datasources

import com.example.domain.models.Coordinates

interface LocationInfoLocalDatasource {
    var currentCoordinates: Coordinates?
}
