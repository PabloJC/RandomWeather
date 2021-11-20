package com.example.randomweather.di

import android.app.Application
import com.example.data.datasources.LocationInfoLocalDatasource
import com.example.data.datasources.LocationInfoRemoteDatasource
import com.example.data.utils.LocationProvider
import com.example.randomweather.BuildConfig
import com.example.randomweather.data.local.cache.LocationInfoCachedDatasource
import com.example.randomweather.data.remote.retrofit.LocationInfoRetrofitDatasource
import com.example.randomweather.data.remote.retrofit.api.RetrofitApiClient
import com.example.randomweather.data.remote.retrofit.api.RetrofitApiService
import com.example.randomweather.data.remote.retrofit.interceptors.WeatherApiInterceptor
import com.example.randomweather.data.utils.LocationProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocationInfoLocalDatasource(): LocationInfoLocalDatasource = LocationInfoCachedDatasource()

    @Provides
    @Singleton
    fun provideLocationInfoRemoteDatasource(service: RetrofitApiService): LocationInfoRemoteDatasource =
            LocationInfoRetrofitDatasource(service)

    @Provides
    @Singleton
    fun provideRetrofitApiClient(@ApiHost baseUrl: String, interceptor: WeatherApiInterceptor) =
            RetrofitApiClient(baseUrl, interceptor)

    @Provides
    @Singleton
    fun provideLocationProvider(app: Application): LocationProvider = LocationProviderImpl(app)

    @Provides
    fun provideRetrofitApiService(client: RetrofitApiClient): RetrofitApiService = client.service

    @Provides
    fun provideWeatherApiInterceptor(@ApiId appId: String) = WeatherApiInterceptor(appId)

    @IODispatcher
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @ApiHost
    @Provides
    fun provideApiHost(): String = BuildConfig.API_HOST

    @ApiId
    @Provides
    fun provideAppId(): String = BuildConfig.API_ID
}