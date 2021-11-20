package com.example.randomweather.ui.map

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.data.utils.PermissionChecker
import com.example.domain.DomainError
import com.example.domain.NoDataFound
import com.example.domain.NoLocation
import com.example.domain.models.Coordinates
import com.example.domain.models.LocationInfo
import com.example.randomweather.R
import com.example.randomweather.data.utils.getLatLng
import com.example.randomweather.databinding.ActivityMapsBinding
import com.example.randomweather.ui.utils.extensions.gone
import com.example.randomweather.ui.utils.extensions.showAnimationRotate
import com.example.randomweather.ui.utils.extensions.showToast
import com.example.randomweather.ui.utils.extensions.visible
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private var currentMarker: Marker? = null
    private lateinit var map: GoogleMap
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<CardView>
    private lateinit var binding: ActivityMapsBinding

    private val viewModel by viewModels<MapViewModel>()

    @Inject
    lateinit var permissionChecker: PermissionChecker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initMap()
        initFab()

        viewModel.currentlocationInfo.observe(this@MapActivity, ::handleUILocation)
        viewModel.uiErrors.observe(this, ::handleUIError)

        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet.root)

        checkPermissions()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }

    private fun initMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun initFab() {
        binding.fabRefresh.run {
            setOnClickListener {
                showAnimationRotate()
                viewModel.getNewLocation()
            }
        }
    }

    private fun handleUIError(error: DomainError?) {
        when (error) {
            NoDataFound -> showToast(R.string.error_no_data_found)
            NoLocation -> showToast(R.string.error_no_location)
        }
    }

    private fun handleUILocation(locationInfo: LocationInfo?) {
        locationInfo?.run {
            getLatLng()?.let { latLng -> moveToLocation(latLng) }
            setCityName(name, coordinates)
            setWeather(weatherIconUrl, weatherDescription)
            setTemperature(temperature)
            setHumidity(humidity)
        }
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun moveToLocation(latLng: LatLng) {
        currentMarker?.remove()
        currentMarker = map.addMarker(MarkerOptions().position(latLng))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11f))
    }

    private fun setCityName(name: String?, coordinates: Coordinates?) {
        binding.bottomSheet.tvCityName.text = if (name.isNullOrEmpty()) {
            coordinates?.let { "${it.latitude} , ${it.longitude}" }
        } else {
            name
        }
    }

    private fun setWeather(weatherIconUrl: String?, weatherDescription: String?) {
        binding.bottomSheet.run {
            weatherIconUrl?.let { iconWeather.load(it) }
            weatherDescription?.let { tvWeatherName.text = it }

            if (!weatherIconUrl.isNullOrEmpty() || !weatherDescription.isNullOrEmpty()) {
                llWeather.visible()
            } else {
                llWeather.gone()
            }
        }
    }

    private fun setTemperature(temperature: Double?) {
        binding.bottomSheet.run {
            temperature?.let {
                tvTemperature.text = String.format("%.2fº", it)
                llTemperature.visible()
            } ?: llTemperature.gone()
        }
    }

    private fun setHumidity(humidity: Long?) {
        binding.bottomSheet.run {
            humidity?.let {
                tvHumidity.text = "$it%"
                llHumidity.visible()
            } ?: llHumidity.gone()
        }
    }

    private fun checkPermissions() {
        lifecycleScope.launchWhenCreated {
            val hasPermission = permissionChecker.checkForPermission(PermissionChecker.Permission.LOCATION)
            if (hasPermission) {
                viewModel.getNewLocation()
            } else {
                showToast(R.string.error_no_location_permission)
            }
        }
    }
}