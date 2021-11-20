package com.example.data.utils

interface PermissionChecker {
    enum class Permission{
        LOCATION
    }
    suspend fun checkForPermission(permission: Permission): Boolean
}