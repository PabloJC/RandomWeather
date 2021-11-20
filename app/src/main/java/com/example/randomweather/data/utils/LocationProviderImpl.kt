package com.example.randomweather.data.utils

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import com.example.data.utils.LocationProvider
import com.example.domain.DomainError
import com.example.domain.Either
import com.example.domain.models.Coordinates
import com.example.domain.NoLocation
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Collections
import java.util.Random
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class LocationProviderImpl @Inject constructor(private val application: Application): LocationProvider {

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(application)
    }

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Either<DomainError, Coordinates> {
        return suspendCancellableCoroutine { continuation  ->
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                val currentLocation = location.toCoordinates()
                Log.d("PJC","currentLocation: $currentLocation")
                continuation.resume(Either.Right(currentLocation))
            }.addOnCanceledListener {
                continuation.resume(Either.Left(NoLocation))
            }
        }
    }

    override suspend fun getRandomLocation(location: Coordinates): Either<DomainError, Coordinates> {
        val randomPoints: MutableList<Coordinates> = ArrayList()
        val randomDistances: MutableList<Float> = ArrayList()

        val currentLocation = location.toLocation()

        //This is to generate 10 random points
        repeat(MAX_POINTS) {
            val randomLocation = createRandomLocation(location)
            randomPoints.add(randomLocation)
            val newLocation = randomLocation.toLocation()
            randomDistances.add(newLocation.distanceTo(currentLocation))
        }

        //Get nearest point to the centre
        val indexOfNearestPointToCentre = randomDistances.indexOf(Collections.min(randomDistances))
        return Either.Right(randomPoints[indexOfNearestPointToCentre])
    }

    private fun createRandomLocation(location: Coordinates): Coordinates {
        val x0 = location.latitude
        val y0 = location.longitude
        val random = Random()

        // Convert radius from meters to degrees
        val radiusInDegrees = (RADIUS / 111000f).toDouble()
        val u: Double = random.nextDouble()
        val v: Double = random.nextDouble()
        val w = radiusInDegrees * sqrt(u)
        val t = 2 * Math.PI * v
        val x = w * cos(t)
        val y = w * sin(t)

        // Adjust the x-coordinate for the shrinking of the east-west distances
        val newX = x / cos(y0)
        val foundLatitude = newX + x0
        val foundLongitude = y + y0
        return Coordinates(foundLatitude, foundLongitude)
    }

    companion object{
        private const val RADIUS = 500000
        private const val MAX_POINTS = 10
    }
}