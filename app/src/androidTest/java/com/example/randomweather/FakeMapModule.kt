package com.example.randomweather.ui.map

import com.example.data.datasources.LocationInfoLocalDatasource
import com.example.data.datasources.LocationInfoRemoteDatasource
import com.example.data.utils.LocationProvider
import com.example.randomweather.di.AppModule
import com.example.randomweather.di.IODispatcher
import com.example.testshared.FakeLocationInfoLocalDatasource
import com.example.testshared.FakeLocationInfoRemoteDatasource
import com.example.testshared.FakeLocationProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@TestInstallIn(components = [SingletonComponent::class], replaces = [AppModule::class]
)
@Module
class FakeAppModule {

    @Provides
    @Singleton
    fun provideLocationInfoLocalDatasource(): LocationInfoLocalDatasource = FakeLocationInfoLocalDatasource()

    @Provides
    @Singleton
    fun provideLocationInfoRemoteDatasource(): LocationInfoRemoteDatasource = FakeLocationInfoRemoteDatasource()

    @Provides
    @Singleton
    fun provideLocationProvider(): LocationProvider = FakeLocationProvider()

    @IODispatcher
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.Unconfined
}