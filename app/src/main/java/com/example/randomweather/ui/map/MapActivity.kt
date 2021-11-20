package com.example.randomweather.ui.map

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.data.utils.PermissionChecker
import com.example.domain.DomainError
import com.example.domain.NoDataFound
import com.example.domain.NoLocation
import com.example.domain.models.LocationInfo
import com.example.randomweather.R
import com.example.randomweather.data.utils.getLatLng
import com.example.randomweather.databinding.ActivityMapsBinding
import com.example.randomweather.ui.utils.extensions.showAnimationRotate
import com.example.randomweather.ui.utils.extensions.showToast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private var currentMarker: Marker? = null
    private lateinit var map: GoogleMap
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
            binding.bottomSheet.tvCityName.text = if(name.isNullOrEmpty()) {
                coordinates?.let { "${it.latitude} , ${it.longitude}" }
            }else{
                name
            }
        }
    }

    private fun moveToLocation(latLng: LatLng) {
        currentMarker?.remove()
        currentMarker = map.addMarker(MarkerOptions().position(latLng))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11f))
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