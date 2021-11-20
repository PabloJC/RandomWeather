package com.example.randomweather.di

import com.example.data.repositories.LocationInfoRepository
import com.example.usecases.GetNewLocationInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MapModule {

    @Provides
    fun provideGetNewLocationInfo(locationInfoRepository: LocationInfoRepository) =
            GetNewLocationInfo(locationInfoRepository)
}