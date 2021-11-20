package com.example.randomweather.di

import com.example.data.datasources.LocationInfoLocalDatasource
import com.example.data.datasources.LocationInfoRemoteDatasource
import com.example.data.repositories.LocationInfoRepository
import com.example.data.repositories.LocationInfoRepositoryImpl
import com.example.data.utils.LocationProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatasModule {
    @Provides
    fun provideLocationInfoRepository(
            remoteDatasource: LocationInfoRemoteDatasource,
            localDatasource: LocationInfoLocalDatasource,
            locationProvider: LocationProvider,
    ): LocationInfoRepository = LocationInfoRepositoryImpl(remoteDatasource, localDatasource, locationProvider)
}