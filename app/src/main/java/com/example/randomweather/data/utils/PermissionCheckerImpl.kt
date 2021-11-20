package com.example.randomweather.data.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.data.utils.PermissionChecker
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class PermissionCheckerImpl @Inject constructor(private val activity: Activity?): PermissionChecker {

    override suspend fun checkForPermission(permission: PermissionChecker.Permission): Boolean {
        return activity?.run {
            when {
                hasPermission(permission) -> true
                else -> requestPermission(permission)
            }
        } ?: false
    }

    private suspend fun requestPermission(permission: PermissionChecker.Permission): Boolean {
        return suspendCancellableCoroutine { continuation ->
            (activity as? AppCompatActivity)?.registerForActivityResult(ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                continuation.resume(isGranted)
            }?.launch(permission.toAndroidId()) ?: continuation.resume(false)
        }
    }

    private fun hasPermission(permission: PermissionChecker.Permission): Boolean = activity?.run {
        ContextCompat.checkSelfPermission(applicationContext, permission.toAndroidId()
        ) == PackageManager.PERMISSION_GRANTED
    } ?: false

    private fun PermissionChecker.Permission.toAndroidId() = when (this) {
        PermissionChecker.Permission.LOCATION -> Manifest.permission.ACCESS_FINE_LOCATION
    }
}