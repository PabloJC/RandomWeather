package com.example.randomweather.di

import android.app.Activity
import com.example.data.utils.PermissionChecker
import com.example.randomweather.data.utils.PermissionCheckerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @Provides
    fun providePermissionChecker(activity: Activity): PermissionChecker = PermissionCheckerImpl(activity)
}